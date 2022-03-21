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
import com.example.weatherapplication.data.database.models.forecast.Forecast
import com.example.weatherapplication.databinding.FragmentDetailsForecastBinding
import com.example.weatherapplication.presentation.ui.AppApplication
import com.example.weatherapplication.presentation.common.convertToDate
import com.example.weatherapplication.presentation.viewmodels.viewmodel_classes.DetailsForecastViewModel
import com.example.weatherapplication.presentation.viewmodels.viewmodel_factory.DetailsForecastViewModelFactory
import javax.inject.Inject
import kotlin.math.round
import kotlin.math.roundToInt


class DetailsForecastFragment : Fragment(R.layout.fragment_details_forecast) {

    @Inject
    lateinit var detailsViewModelFactory: DetailsForecastViewModelFactory
    private val detailsViewModel: DetailsForecastViewModel by viewModels { detailsViewModelFactory }

    private val binding by viewBinding(FragmentDetailsForecastBinding::bind)
    private lateinit var currentForecast: Forecast

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
        generateReportWeatherInCity()
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
            currentForecast.sys.sunrise.convertToDate("HH:mm")
        )
        binding.tvValueSunset.text = this.getString(
            R.string.DetailsForecastFragment_date_text,
            currentForecast.sys.sunset.convertToDate("HH:mm")
        )
    }

    private fun setVisibility() {
        binding.tvValueVisibility.text = this.getString(
            R.string.DetailsForecastFragment_text_km,
            currentForecast.visibility / 1000L
        )
    }

    private fun setHumidity() {
        binding.tvValueHumidity.text =
            this.getString(
                R.string.DetailsForecastFragment_text_percent,
                currentForecast.main.humidity
            )
    }

    private fun setPressure() {
        binding.tvValuePressure.text = this.getString(
            R.string.DetailsForecastFragment_text_pressure,
            currentForecast.main.pressure
        )
    }

    private fun setWind() {
        rotationIconWind()
        setInfoTextWind()
    }

    private fun setInfoTextWind() {
        binding.tvValueWind.text =
            this.getString(
                R.string.DetailsForecastFragment_text_ms,
                currentForecast.wind.speed.roundToInt()
            )
    }

    private fun rotationIconWind() {
        binding.imvIcWindRoute.animate().rotation(currentForecast.wind.routeDegrees.toFloat())
    }

    private fun setDescription() {
        binding.tvDescription.text = currentForecast.weather[0].description
    }

    private fun setIcon() {
        Glide.with(requireContext())
            .load(
                this.getString(
                    R.string.URL_loading_image_weather,
                    currentForecast.weather[0].iconId
                )
            )
            .placeholder(R.drawable.ic_cloud)
            .error(R.drawable.ic_no_internet)
            .into(binding.imvIcWeather)
    }

    private fun setTemp() {
        binding.tvValueTemp.text = round(currentForecast.main.temperature).toInt().toString()
    }

    private fun setNameCity() {
        binding.tvCityName.text = this.getString(
            R.string.DetailsForecastFragment_text_name_city,
            currentForecast.cityName,
            currentForecast.sys.country
        )
    }

    private fun setTime() {
        binding.tvDateTimeLastUpdate.text = this.getString(
            R.string.DetailsForecastFragment_updateText_text,
            currentForecast.timeForecast.convertToDate("dd MMM yyyy HH:mm")
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

    private fun generateReportWeatherInCity() {
        binding.fabOpenReport.visibility = View.VISIBLE

        binding.fabOpenReport.setOnClickListener {
            binding.fabOpenReport.visibility = View.INVISIBLE

            val bundle = Bundle()

            bundle.putParcelable(KEY_FORECAST, currentForecast)

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

