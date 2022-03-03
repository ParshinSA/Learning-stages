package com.example.weatherapplication.ui.weather.short_forecast

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherapplication.R
import com.example.weatherapplication.data.db.app_sp.SharedPrefs
import com.example.weatherapplication.data.db.app_sp.SharedPrefsContract
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.objects.CustomCities
import com.example.weatherapplication.databinding.FragmentShortForecastListBinding
import com.example.weatherapplication.ui.AppApplication
import com.example.weatherapplication.utils.ItemDecoration
import com.google.android.material.transition.MaterialElevationScale
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ShortForecastListFragment : Fragment(R.layout.fragment_short_forecast_list) {

    @Inject
    lateinit var shortViewModelFactory: ShortForecastViewModelFactory

    @Inject
    lateinit var customCities: CustomCities

    private val shortViewModel: ShortForecastViewModel by viewModels { shortViewModelFactory }

    private val bind by viewBinding(FragmentShortForecastListBinding::bind)

    private val sharedPrefs: SharedPreferences by lazy {
        SharedPrefs.instancePrefs
    }

    private lateinit var adapterRVForecast: ForecastListAdapterRV
    private var myDialog: AlertDialog? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: ")
        thisTransition(view)
        actionInFragment()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun actionInFragment() {
        initComponents()
        addNewCity()
        refreshForecastListSwipe()
        exitEnterTransition()
        observeData()
    }

    private fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    private fun initComponents() {
        initRV()
    }

    private fun addNewCity() {
        bind.fabAddCity.setOnClickListener {
            findNavController().navigate(R.id.action_shortForecastListFragment_to_addCityFragment)
        }
    }

    private fun setLastTimeUpdateForecast() {
        val lastTimeUpdate = sharedPrefs.getLong(SharedPrefsContract.TIME_LAST_REQUEST_KEY, 0L)
        val dateFormat = Date(lastTimeUpdate)
        val sdf =
            SimpleDateFormat(
                this.getString(R.string.ShortForecastListFragment_time_format_ddMMMMHHmmss),
                Locale("ru")
            ).format(dateFormat)
        bind.tvLastUpdateTime.text = this.getString(
            R.string.ShortForecastListFragment_updateText_text,
            sdf
        )
    }

    private fun isConnect(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) != null
    }

    private fun changeStateViewUpdate(state: Boolean) {
        bind.tvTextHeader.text =
            if (!state)
                this.getString(R.string.ShortForecastListFragment_text_No_city_in_list)
            else this.getString(R.string.ShortForecastListFragment_text_Click_for_detailed_forecast)
        bind.tvLastUpdateTime.isVisible = state
    }

    private fun getForecastList() {
        if (isConnect()) {
            shortViewModel.getForecastList()
        } else {
            shortViewModel.errorMessage(this.getString(R.string.ShortForecastListFragment_check_internet))
            shortViewModel.isLoadingMutableLiveData.postValue(false)
        }
    }

    private fun changeStateProgressView(isLoading: Boolean) {
        bind.swlSwipeContainer.isRefreshing = isLoading
    }

    private fun thisTransition(view: View) {
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun exitEnterTransition() {
        exitTransition = MaterialElevationScale(false).apply {
            duration = 200.toLong()
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 200.toLong()
        }
    }

    private fun refreshForecastListSwipe() {
        bind.swlSwipeContainer.setOnRefreshListener {
            Log.d(TAG, "refreshForecastListSwipe: start")
            getForecastList()
        }
    }

    private fun observeData() {
        shortViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { message ->
            showDialogError(message)
        }

        shortViewModel.isLoadingMutableLiveData.observe(viewLifecycleOwner) { isUpdate ->
            changeStateProgressView(isUpdate)
        }

        shortViewModel.forecastListLiveData.observe(viewLifecycleOwner) { listForecast ->
            Log.d(TAG, "observeData: forecastListLiveData $listForecast")
            updateForecastListInRV(listForecast)
        }

        customCities.listCitiesLiveData.observe(viewLifecycleOwner) { listCustomCities ->
            Log.d(TAG, "observeData: listCitiesLiveData $listCustomCities")
            if (listCustomCities.isNotEmpty()) getForecastList()
        }
    }

    private fun updateForecastListInRV(listForecast: List<Forecast>) {
        if (listForecast.isEmpty())
            changeStateViewUpdate(false)
        else {
            adapterRVForecast.submitList(listForecast.sortedBy {
                it.cityName
            })
            changeStateViewUpdate(true)
            setLastTimeUpdateForecast()
        }
    }

    private fun showDialogError(message: String) {
        myDialog = AlertDialog.Builder(requireContext())
            .setTitle(this.getString(R.string.ShortForecastListFragment_attention))
            .setMessage(message)
            .create()

        myDialog!!.show()
    }

    private fun initRV() {
        adapterRVForecast =
            ForecastListAdapterRV { position: Int, currentViewInRV: View ->
                transitionInDetailsForecastFragment(position, currentViewInRV)
            }

        with(bind.rvListCity) {
            adapter = adapterRVForecast
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(ItemDecoration(requireContext(), 10))
            setHasFixedSize(true)
        }
    }

    private fun transitionInDetailsForecastFragment(position: Int, currentView: View) {
        val forecast = shortViewModel.forecastListLiveData.value!![position]
        val bundle = Bundle()
        bundle.putParcelable(KEY_FORECAST, forecast)

        val transitionName =
            this.resources.getString(R.string.DetailsForecastFragment_transition_name)
        val extras = FragmentNavigatorExtras(currentView to transitionName)

        findNavController().navigate(
            R.id.action_shortForecastListFragment_to_detailsForecastFragment, bundle, null, extras
        )
    }

    override fun onPause() {
        Log.d(TAG, "onPause: ")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop: ")
        super.onStop()
    }

    override fun onDestroy() {
        myDialog?.dismiss()
        Log.d(TAG, "onDestroy:")
        super.onDestroy()
    }

    companion object {
        const val KEY_FORECAST = "key forecast"
        const val TAG = "ShortFrg_Logging"
    }
}