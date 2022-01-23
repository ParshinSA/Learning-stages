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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapplication.R
import com.example.weatherapplication.data.db.appsp.SharedPrefs
import com.example.weatherapplication.data.db.appsp.SharedPrefsContract
import com.example.weatherapplication.data.db.appsp.SharedPrefsListener
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.databinding.FragmentShortForecastListBinding
import com.example.weatherapplication.services.StateServiceUpdateForecast
import com.example.weatherapplication.ui.weather.shortforecastlist.recyclerview.CharacterItemDecoration
import com.example.weatherapplication.ui.weather.shortforecastlist.recyclerview.ShortForecastListAdapterRV
import com.google.android.material.transition.MaterialElevationScale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ShortForecastListFragment : Fragment(R.layout.fragment_short_forecast_list) {

    private var _bind: FragmentShortForecastListBinding? = null
    private val bind: FragmentShortForecastListBinding
        get() = _bind!!

    private val sharedPrefs: SharedPreferences by lazy {
        SharedPrefs.instancePrefs
    }

    private lateinit var adapterRVShortForecast: ShortForecastListAdapterRV
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
        initComponents()
        observeData()
        swipeUpdateForecastList()
        exitEnterTransition()
        getForecastList(false)
    }

    private fun initComponents() {
        initRV()
    }

    private fun setLastTimeUpdateForecast() {
        val lastTimeUpdate = sharedPrefs.getLong(SharedPrefsContract.TIME_LAST_REQUEST_KEY, 0L)
        Log.d(TAG, "setLastTimeUpdateForecast: lastTime:$lastTimeUpdate")
        val dateFormat = Date(lastTimeUpdate)
        val sdf =
            SimpleDateFormat(this.getString(R.string.ShortForecastListFragment_time_format_ddMMMMHHmmss))

        bind.updateTime.text = this.getString(
            R.string.ShortForecastListFragment_updateText_text,
            sdf.format(dateFormat)
        )
    }

    private fun isConnect(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) != null
    }

    private fun changeStateViewUpdateTime(state: Boolean) {
        bind.updateTime.isVisible = state
    }

    private fun getForecastList(isForcedUpdate: Boolean) {

        if (isForcedUpdate) {
            if (isConnect()) {
                shortForecastListViewModel.getForecastList(isForcedUpdate)
                changeStateViewUpdateTime(true)
            } else {
                shortForecastListViewModel.errorMessage(this.getString(R.string.ShortForecastListFragment_check_internet))
                changeStateViewUpdateTime(false)
                changeStateProgressView(false)
            }

        } else shortForecastListViewModel.getForecastList(isForcedUpdate)
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

    private fun swipeUpdateForecastList() {
        bind.swipeLayout.setOnRefreshListener {
            getForecastList(true)
        }
    }

    private fun observeData() {
        shortForecastListViewModel.forecastListLiveData.observe(viewLifecycleOwner) { listForecast ->
            updateForecastListInRV(listForecast)
        }

        shortForecastListViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { message ->
            showDialogError(message)
        }

        shortForecastListViewModel.isLoadingLiveData.observe(viewLifecycleOwner) { isUpdate ->
            changeStateProgressView(isUpdate)
        }

        observeUpdateService()
        observeSharedPrefs()
    }

    private fun updateForecastListInRV(listForecast: List<Forecast>) {
        if (listForecast.isEmpty()) {
            shortForecastListViewModel.errorMessage("Не удалось получить данные")
        } else adapterRVShortForecast.submitList(listForecast)
    }

    private fun observeSharedPrefs() {
        lifecycleScope.launch(Dispatchers.IO) {
            Log.d(TAG, "observeSharedPrefs: ")
            SharedPrefs.addChangeListener()
        }
        SharedPrefsListener.listenerSharedPrefs.observe(viewLifecycleOwner) {
            Log.d(TAG, "observeSharedPrefs: UPDATE TIME")
            setLastTimeUpdateForecast()
        }
    }

    private fun observeUpdateService() {
        StateServiceUpdateForecast.stateUpdate.observe(viewLifecycleOwner) { isUpdate ->
            changeStateProgressView(isUpdate)
            if (!isUpdate) getForecastList(false)
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
        adapterRVShortForecast =
            ShortForecastListAdapterRV { position: Int, currentViewInRV: View ->
                transitionInDetailsForecastFragment(position, currentViewInRV)
            }

        with(bind.listOfCityRV) {
            adapter = adapterRVShortForecast
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(CharacterItemDecoration(requireContext(), 10))
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
        SharedPrefs.removeListener()
        myDialog?.dismiss()
        Log.d(TAG, "onDestroy:")
        super.onDestroy()
    }

    companion object {
        const val KEY_FORECAST = "key forecast"
        const val TAG = "ShortFrg_Logging"
    }
}