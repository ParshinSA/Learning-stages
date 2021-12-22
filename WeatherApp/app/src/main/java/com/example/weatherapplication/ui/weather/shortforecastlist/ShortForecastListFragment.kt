package com.example.weatherapplication.ui.weather.shortforecastlist

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapplication.DefaultListCity
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.FragmentShortForecastListBinding
import com.example.weatherapplication.ui.weather.shortforecastlist.recyclerview.CharacterItemDecoration
import com.example.weatherapplication.ui.weather.shortforecastlist.recyclerview.ShortForecastListAdapterRV
import com.example.weatherapplication.utils.logD
import com.google.android.material.transition.MaterialElevationScale

class ShortForecastListFragment : Fragment(R.layout.fragment_short_forecast_list) {

    private var _bind: FragmentShortForecastListBinding? = null
    private val bind: FragmentShortForecastListBinding
        get() = _bind!!

    private lateinit var adapterRVShortForecast: ShortForecastListAdapterRV
    private val shortForecastListViewModel: ShortForecastListViewModel by viewModels()
    private val defaultListCity = DefaultListCity.listCity
    private var myDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentShortForecastListBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.logD("onViewCreated")
        thisTransition(view)
        actionInFragment()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun thisTransition(view: View) {
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun actionInFragment() {
        initRV()
        checkInternet()
        observeData()
        swipeUpdateForecastList()
        getForecastList()
        exitEnterTransition()
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
            updateForecastList()
        }
    }

    private fun getForecastList() {
        this.logD("getForecastList")
        shortForecastListViewModel.getForecastList(defaultListCity)
    }

    private fun updateForecastList() {
        this.logD("updateForecastList")
        shortForecastListViewModel.updateForecastList(defaultListCity)
    }

    private fun observeData() {
        shortForecastListViewModel.forecastListLiveData.observe(viewLifecycleOwner) {
            adapterRVShortForecast.submitList(it)
            this.logD("submitList $it")
        }

        shortForecastListViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { message ->
            showDialogError(message)
        }

        shortForecastListViewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            bind.swipeLayout.isRefreshing = it
        }
    }

    private fun showDialogError(message: String) {
        myDialog = AlertDialog.Builder(requireContext())
            .setTitle(this.getString(R.string.ShortForecastListFragment_attention))
            .setMessage(message)
            .create()

        myDialog!!.show()
    }

    private fun checkInternet() {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isConnect =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) == null
        if (isConnect) shortForecastListViewModel.errorMessage(this.getString(R.string.ShortForecastListFragment_check_internet))
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
        bundle.putParcelable(KEY, forecast)

        val transitionName =
            this.resources.getString(R.string.DetailsForecastFragment_transition_name)
        val extras = FragmentNavigatorExtras(currentView to transitionName)

        findNavController().navigate(
            R.id.action_shortForecastListFragment_to_detailsForecastFragment, bundle, null, extras
        )
    }

    override fun onDestroy() {
        myDialog?.dismiss()
        super.onDestroy()
    }

    companion object {
        const val KEY = "key"
    }
}