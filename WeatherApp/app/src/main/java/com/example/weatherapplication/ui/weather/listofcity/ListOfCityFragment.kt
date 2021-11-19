package com.example.weatherapplication.ui.weather.listofcity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapplication.R
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.databinding.FragmentListOfCityBinding
import com.example.weatherapplication.ui.weather.ForecastViewModel

class ListOfCityFragment : Fragment(R.layout.fragment_list_of_city) {

    private var _bind: FragmentListOfCityBinding? = null
    private val bind: FragmentListOfCityBinding
        get() = _bind!!

    private lateinit var adapterRV: ListOfCityAdapterRV
    private val viewModel: ForecastViewModel by viewModels()
    private val defaultListCity = DefaultListCity.listCity
    private var myDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentListOfCityBinding.inflate(inflater, container, false)
        actionInFragment()
        return bind.root
    }

    private fun actionInFragment() {
        initRV()
        observeData()
        getForecast()
        swipeUpdateForecastList()
        checkInternet()
    }

    private fun swipeUpdateForecastList() {
        bind.swipeLayout.setOnRefreshListener {
            getForecast()
        }
    }

    private fun getForecast() {
        viewModel.getWeatherForecast(defaultListCity)
    }

    private fun observeData() {
        viewModel.forecastList.observe(viewLifecycleOwner) {
            adapterRV.submitList(it)
            bind.swipeLayout.isRefreshing = false
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            showDialogError(message)
        }
    }

    private fun showDialogError(message: String) {
        myDialog = AlertDialog.Builder(requireContext())
            .setTitle("Внимание!")
            .setMessage(message)
            .create()

        myDialog!!.show()
    }

    private fun checkInternet() {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isConnect =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) == null
        if (isConnect) showDialogError("Проверьте подключение к интернету")
    }

    private fun initRV() {
        adapterRV = ListOfCityAdapterRV() {
            initCityInfoFragment(
                viewModel.forecastList.value!![it]
            )
        }
        with(bind.listOfCityRV) {
            adapter = adapterRV
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
        super.onDestroy()
        myDialog?.dismiss()
    }

    companion object {
        const val KEY = "key"
    }
}