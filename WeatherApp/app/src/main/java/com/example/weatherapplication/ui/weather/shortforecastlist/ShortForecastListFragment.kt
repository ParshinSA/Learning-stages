package com.example.weatherapplication.ui.weather.shortforecastlist

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapplication.DefaultListCity
import com.example.weatherapplication.R
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.databinding.FragmentListOfCityBinding
import com.example.weatherapplication.utils.logD

class ShortForecastListFragment : Fragment(R.layout.fragment_list_of_city) {

    private var _bind: FragmentListOfCityBinding? = null
    private val bind: FragmentListOfCityBinding
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
        _bind = FragmentListOfCityBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.logD("onViewCreated")
        actionInFragment()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun actionInFragment() {
        initRV()
        observeData()
        swipeUpdateForecastList()
        checkInternet()
        getForecastList()
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
        adapterRVShortForecast = ShortForecastListAdapterRV {
            initCityInfoFragment(
                shortForecastListViewModel.forecastListLiveData.value!![it]
            )
        }
        with(bind.listOfCityRV) {
            adapter = adapterRVShortForecast
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun initCityInfoFragment(forecast: Forecast) {
        val bundle = Bundle()
        bundle.putParcelable(KEY, forecast)
        findNavController().navigate(R.id.action_listOfCityFragment_to_cityInfoFragment, bundle)
    }

    override fun onDestroy() {
        myDialog?.dismiss()
        super.onDestroy()
    }

    companion object {
        const val KEY = "key"
    }
}