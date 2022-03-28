package com.example.weatherapplication.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.FragmentDetailsForecastBinding
import com.example.weatherapplication.presentation.models.forecast.details_forecast.UiDetailsForecast
import com.example.weatherapplication.presentation.ui.AppApplication
import com.example.weatherapplication.presentation.viewmodels.viewmodel_classes.DetailsForecastViewModel
import com.example.weatherapplication.presentation.viewmodels.viewmodel_factory.DetailsForecastViewModelFactory
import javax.inject.Inject


class DetailsForecastFragment : Fragment(R.layout.fragment_details_forecast) {

    @Inject
    lateinit var detailsViewModelFactory: DetailsForecastViewModelFactory
    private val detailsViewModel: DetailsForecastViewModel by viewModels { detailsViewModelFactory }

    private val binding by viewBinding(FragmentDetailsForecastBinding::bind)
    private lateinit var currentForecast: UiDetailsForecast

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        actionInFragment()
    }

    private fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    private fun actionInFragment() {
        sendForecastInViewModel()
        transitionInReportFragment()
        observeData()
        backButtonClickListener()
    }

    private fun observeData() {
        detailsViewModel.detailsForecastLiveData.observe(viewLifecycleOwner) { forecast ->
            Log.d(TAG, "observeData: ")
            currentForecast = forecast
            bindViewItems()
        }
    }

    private fun bindViewItems() {
        setTime()
        setNameCity()
        setTemp()
        setIcon()
        setDescription()
        setWind()
        setPressure()
        setHumidity()
        setVisibility()
        setTimeSunriseAndSunset()
    }

    private fun backButtonClickListener() {
        binding.tbDetailFrg.setNavigationOnClickListener {
            navigateUp(findNavController(), null)
        }
    }

    private fun setTimeSunriseAndSunset() {
        binding.tvValueSunrise.text = this.getString(
            R.string.DetailsForecastFragment_date_text,
            currentForecast.sunrise
        )
        binding.tvValueSunset.text = this.getString(
            R.string.DetailsForecastFragment_date_text,
            currentForecast.sunset
        )
    }

    private fun setVisibility() {
        binding.tvValueVisibility.text = this.getString(
            R.string.DetailsForecastFragment_text_km,
            currentForecast.visibility
        )
    }

    private fun setHumidity() {
        binding.tvValueHumidity.text = this.getString(
            R.string.DetailsForecastFragment_text_percent,
            currentForecast.humidity
        )
    }

    private fun setPressure() {
        binding.tvValuePressure.text = this.getString(
            R.string.DetailsForecastFragment_text_pressure,
            currentForecast.pressure
        )
    }

    private fun setWind() {
        rotationIconWind()
        setInfoTextWind()
    }

    private fun setInfoTextWind() {
        binding.tvValueWind.text = this.getString(
            R.string.DetailsForecastFragment_text_ms,
            currentForecast.windSpeed
        )
    }

    private fun rotationIconWind() {
        binding.imvIcWindRoute.animate().rotation(currentForecast.windDirectionDegrees)
    }

    private fun setDescription() {
        binding.tvDescription.text = currentForecast.description
    }

    private fun setIcon() {
        Glide.with(requireContext())
            .load(
                this.getString(
                    R.string.URL_loading_image_weather,
                    currentForecast.iconId
                )
            )
            .placeholder(R.drawable.ic_cloud)
            .error(R.drawable.ic_no_internet)
            .into(binding.imvIcWeather)
    }

    private fun setTemp() {
        binding.tvValueTemp.text = currentForecast.temperature
    }

    private fun setNameCity() {
        binding.tvCityName.text = this.getString(
            R.string.DetailsForecastFragment_text_name_city,
            currentForecast.cityName,
            currentForecast.country
        )
    }

    private fun setTime() {
        binding.tvDateTimeLastUpdate.text = this.getString(
            R.string.DetailsForecastFragment_updateText_text,
            currentForecast.forecastTime
        )
    }

    private fun getArgument() {
        currentForecast = requireArguments().getParcelable(ShortForecastFragment.KEY_FORECAST)
            ?: error("$TAG No forecast")
    }

    private fun sendForecastInViewModel() {
        getArgument()
        detailsViewModel.setDataDetailsForecastInView(currentForecast)
    }

    private fun transitionInReportFragment() {
        binding.fabOpenReport.visibility = View.VISIBLE

        binding.fabOpenReport.setOnClickListener {
            val currentCity = detailsViewModel.currentCity()
            binding.fabOpenReport.visibility = View.INVISIBLE

            val bundle = Bundle()

            bundle.putParcelable(KEY_FORECAST, currentCity)

            findNavController().navigate(
                R.id.action_detailsForecastFragment_to_weatherReportFragment,
                bundle,
                null,
                null
            )
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy:")
        super.onDestroy()
    }

    companion object {
        const val KEY_FORECAST = "key forecast"
        const val TAG = "DetailFrg_Logging"
    }
}

