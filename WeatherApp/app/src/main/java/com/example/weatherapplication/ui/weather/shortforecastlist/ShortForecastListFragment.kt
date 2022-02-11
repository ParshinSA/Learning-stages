package com.example.weatherapplication.ui.weather.shortforecastlist

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapplication.R
import com.example.weatherapplication.data.db.appsp.SharedPrefs
import com.example.weatherapplication.data.db.appsp.SharedPrefsContract
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.databinding.FragmentShortForecastListBinding
import com.example.weatherapplication.ui.weather.shortforecastlist.recyclerview.ForecastListAdapterRV
import com.example.weatherapplication.ui.weather.shortforecastlist.recyclerview.ItemDecoration
import com.google.android.material.transition.MaterialElevationScale
import java.text.SimpleDateFormat
import java.util.*

class ShortForecastListFragment : Fragment(R.layout.fragment_short_forecast_list) {

    private var _bind: FragmentShortForecastListBinding? = null
    private val bind: FragmentShortForecastListBinding
        get() = _bind!!

    private val sharedPrefs: SharedPreferences by lazy {
        SharedPrefs.instancePrefs
    }

    private lateinit var adapterRVForecast: ForecastListAdapterRV
    private val shortForecastListViewModel: ShortForecastListViewModel by viewModels()
    private var myDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ")
        _bind = FragmentShortForecastListBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: ")
        thisTransition(view)
        actionInFragment()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun actionInFragment() {
        getForecastList()
        initComponents()
        addNewCity()
        refreshForecastListSwipe()
        exitEnterTransition()
        observeData()
    }

    private fun addNewCity() {
        bind.addCityFab.setOnClickListener {
            findNavController().navigate(R.id.action_shortForecastListFragment_to_addCityFragment)
        }
    }

    private fun initComponents() {
        initRV()
    }

    private fun setLastTimeUpdateForecast() {
        val lastTimeUpdate = sharedPrefs.getLong(SharedPrefsContract.TIME_LAST_REQUEST_KEY, 0L)
        val dateFormat = Date(lastTimeUpdate)
        val sdf =
            SimpleDateFormat(
                this.getString(R.string.ShortForecastListFragment_time_format_ddMMMMHHmmss),
                Locale("ru")
            ).format(dateFormat)
        bind.updateTime.text = this.getString(
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
        bind.textHeader.text =
            if (!state) "Вы ещё не добавили ни одного города." else "Нажмите для подробного прогноза."
        bind.updateTime.isVisible = state
    }

    private fun getForecastList() {
        if (isConnect()) {
            shortForecastListViewModel.getForecastList()
        } else {
            shortForecastListViewModel.errorMessage(this.getString(R.string.ShortForecastListFragment_check_internet))
            changeStateProgressView(false)
        }
    }

    private fun changeStateProgressView(isLoading: Boolean) {
        bind.swipeLayout.isRefreshing = isLoading
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
        bind.swipeLayout.setOnRefreshListener {
            Log.d(TAG, "refreshForecastListSwipe: start")
            getForecastList()
        }
    }

    private fun observeData() {
        shortForecastListViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { message ->
            showDialogError(message)
        }

        shortForecastListViewModel.isLoadingLiveData.observe(viewLifecycleOwner) { isUpdate ->
            changeStateProgressView(isUpdate)
        }

        shortForecastListViewModel.forecastListLiveData.observe(viewLifecycleOwner) { listForecast ->
            updateForecastListInRV(listForecast)
        }
    }

    private fun updateForecastListInRV(listForecast: List<Forecast>) {
        if (listForecast.isEmpty())
            changeStateViewUpdate(false)
        else {
            adapterRVForecast.submitList(listForecast.sortedBy { Forecast::cityName.toString() })
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

        with(bind.listOfCityRV) {
            adapter = adapterRVForecast
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(ItemDecoration(requireContext(), 10))
            setHasFixedSize(true)
        }
    }

    private fun transitionInDetailsForecastFragment(position: Int, currentView: View) {
        val forecast = shortForecastListViewModel.forecastListLiveData.value!![position]
        val bundle = Bundle()
        bundle.putParcelable(KEY_FORECAST, forecast)

        val transitionName =
            this.resources.getString(R.string.DetailsForecastFragment_transition_name)
        val extras = FragmentNavigatorExtras(currentView to transitionName)

        findNavController().navigate(
            R.id.action_shortForecastListFragment_to_detailsForecastFragment, bundle, null, extras
        )
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