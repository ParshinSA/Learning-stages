package com.example.weatherapplication.ui.weather.detailsforecast

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.objects.CustomCities
import com.example.weatherapplication.databinding.FragmentDetailsForecastBinding
import com.example.weatherapplication.ui.weather.shortforecastlist.ShortForecastListFragment
import com.example.weatherapplication.utils.convertToDate
import com.google.android.material.transition.MaterialContainerTransform
import kotlin.math.round
import kotlin.math.roundToInt


class DetailsForecastFragment : Fragment(R.layout.fragment_details_forecast) {

    private var _bind: FragmentDetailsForecastBinding? = null
    private val bind: FragmentDetailsForecastBinding
        get() = _bind!!

    private val detailsForecastViewModel: DetailsForecastViewModel by viewModels()

    private lateinit var currentForecast: Forecast

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentDetailsForecastBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        thisTransition(view)
        actionInFragment()
    }

    private fun thisTransition(view: View) {
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun actionInFragment() {
        exitTransform()
        sendForecastInViewModel()
        generateReportWeatherInCity()
        observeData()
        backButtonClickListener()
    }

    private fun exitTransform() {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.my_nav_host_fragment
            duration = 300
            scrimColor = Color.TRANSPARENT
        }
    }

    private fun observeData() {
        detailsForecastViewModel.detailsForecastLiveData.observe(viewLifecycleOwner) { forecast ->
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
        bind.toolbarDetailFrg.setNavigationOnClickListener {
            NavigationUI.navigateUp(findNavController(), null)
        }
    }

    private fun setTimeSunriseAndSunset() {
        bind.infoTextSunriseTV.text = this.getString(
            R.string.DetailsForecastFragment_date_text,
            currentForecast.sys.sunrise.convertToDate("HH:mm")
        )
        bind.infoTextSunsetTV.text = this.getString(
            R.string.DetailsForecastFragment_date_text,
            currentForecast.sys.sunset.convertToDate("HH:mm")
        )
    }

    private fun setVisibility() {
        bind.infoTextVisibilityTV.text = this.getString(
            R.string.DetailsForecastFragment_text_km,
            currentForecast.visibility / 1000L
        )
    }

    private fun setHumidity() {
        bind.infoTextHumidityTV.text =
            this.getString(
                R.string.DetailsForecastFragment_text_percent,
                currentForecast.main.humidity
            )
    }

    private fun setPressure() {
        bind.infoTextPressureTV.text = this.getString(
            R.string.DetailsForecastFragment_text_pressure,
            currentForecast.main.pressure
        )
    }

    private fun setWind() {
        rotationIconWind()
        setInfoTextWind()
    }

    private fun setInfoTextWind() {
        bind.infoTextWindTV.text =
            this.getString(
                R.string.DetailsForecastFragment_text_ms,
                currentForecast.wind.speed.roundToInt()
            )
    }

    private fun rotationIconWind() {
        bind.iconWindRouteIV.animate().rotation(currentForecast.wind.routeDegrees.toFloat())
    }

    private fun setDescription() {
        bind.descriptionTV.text = currentForecast.weather[0].description
    }

    private fun setIcon() {
        Glide.with(requireContext())
            .load(
                this.getString(
                    R.string.URL_loading_image_weather,
                    currentForecast.weather[0].sectionURL
                )
            )
            .placeholder(R.drawable.ic_cloud)
            .error(R.drawable.ic_no_internet)
            .into(bind.iconWeatherIV)
    }

    private fun setTemp() {
        bind.tempTV.text = round(currentForecast.main.temp).toInt().toString()
    }

    private fun setNameCity() {
        bind.cityNameTV.text = this.getString(
            R.string.DetailsForecastFragment_text_name_city,
            currentForecast.cityName,
            currentForecast.sys.country
        )
    }

    private fun setTime() {
        bind.dateTimeTV.text = this.getString(
            R.string.DetailsForecastFragment_updateText_text,
            currentForecast.timeForecast.convertToDate("dd MMM yyyy HH:mm")
        )
    }

    private fun getArgument() {
        currentForecast = requireArguments().getParcelable(ShortForecastListFragment.KEY_FORECAST)
            ?: error("$TAG No forecast")
    }

    private fun sendForecastInViewModel() {
        getArgument()
        detailsForecastViewModel.setDataDetailsForecastInView(currentForecast)
    }

    private fun generateReportWeatherInCity() {
        bind.openReportFab.visibility = View.VISIBLE

        bind.openReportFab.setOnClickListener {
            bind.openReportFab.visibility = View.INVISIBLE

            val extras = getMyExtras()

            val bundle = Bundle()

            bundle.putParcelable(KEY_FORECAST, currentForecast)

            findNavController().navigate(
                R.id.action_detailsForecastFragment_to_weatherReportFragment,
                bundle,
                null,
                extras
            )
        }
    }

    private fun getMyExtras(): FragmentNavigator.Extras {
        val transitionName =
            resources.getString(R.string.WeatherReportFragment_transition_name)
        return FragmentNavigatorExtras(bind.openReportFab to transitionName)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy:")
        super.onDestroy()
    }

    companion object {
        const val KEY_FORECAST = "key city name"
        const val TAG = "DetailFrg_Logging"
    }
}

