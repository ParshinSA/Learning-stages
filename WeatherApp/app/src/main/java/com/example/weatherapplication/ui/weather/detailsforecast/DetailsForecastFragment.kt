package com.example.weatherapplication.ui.weather.detailsforecast

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.databinding.FragmentDetailsForecastBinding
import com.example.weatherapplication.ui.weather.shortforecastlist.ShortForecastListFragment
import com.example.weatherapplication.utils.convertToDate
import com.example.weatherapplication.utils.logD
import com.google.android.material.transition.MaterialContainerTransform
import timber.log.Timber
import kotlin.math.round
import kotlin.math.roundToInt


class DetailsForecastFragment : Fragment(R.layout.fragment_details_forecast) {

    private var _bind: FragmentDetailsForecastBinding? = null
    private val bind: FragmentDetailsForecastBinding
        get() = _bind!!

    private val detailsForecastViewModel: DetailsForecastViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.d("onCreateView")

        _bind = FragmentDetailsForecastBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
        thisTransition(view)
        action()
    }

    private fun thisTransition(view: View) {
        Timber.d("thisTransition")

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun action() {
        exitTransform()
        sendForecastInViewModel()
        getArgument()
        observeData()
    }

    private fun exitTransform() {
        Timber.d("exitTransform")

        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.my_nav_host_fragment
            duration = 300
            scrimColor = Color.TRANSPARENT
        }
    }

    private fun observeData() {
        Timber.d("observeData")

        detailsForecastViewModel.detailsForecastLiveData.observe(viewLifecycleOwner) { forecast ->
            this.logD("observeData forecast = $forecast")
            bindViewItems(forecast)
        }
    }

    private fun bindViewItems(forecast: Forecast) {
        setTime(forecast)
        setNameCity(forecast)
        setCountryCity(forecast)
        setTemp(forecast)
        setIcon(forecast)
        setDescription(forecast)
        setWind(forecast)
        setPressure(forecast)
        setHumidity(forecast)
        setVisibility(forecast)
        setTimeSunriseAndSunset(forecast)
    }

    private fun setTimeSunriseAndSunset(forecast: Forecast) {
        Timber.d("setTimeSunriseAndSunset")

        bind.infoTextSunriseTV.text = this.getString(
            R.string.DetailsForecastFragment_date_text,
            forecast.sys.sunrise.convertToDate("HH:mm")
        )
        bind.infoTextSunsetTV.text = this.getString(
            R.string.DetailsForecastFragment_date_text,
            forecast.sys.sunset.convertToDate("HH:mm")
        )
    }

    private fun setVisibility(forecast: Forecast) {
        Timber.d("setVisibility")

        bind.infoTextVisibilityTV.text = this.getString(
            R.string.DetailsForecastFragment_text_km,
            forecast.visibility / 1000L
        )
    }

    private fun setHumidity(forecast: Forecast) {
        Timber.d("setHumidity")

        bind.infoTextHumidityTV.text =
            this.getString(
                R.string.DetailsForecastFragment_text_percent,
                forecast.main.humidity
            )
    }

    private fun setPressure(forecast: Forecast) {
        Timber.d("setPressure")

        bind.infoTextPressureTV.text = this.getString(
            R.string.DetailsForecastFragment_text_pressure,
            forecast.main.pressure
        )
    }

    private fun setWind(forecast: Forecast) {
        Timber.d("setWind")

        rotationIconWind(forecast)
        setInfoTextWind(forecast)
    }

    private fun setInfoTextWind(forecast: Forecast) {
        Timber.d("setInfoTextWind")

        bind.infoTextWindTV.text =
            this.getString(
                R.string.DetailsForecastFragment_text_ms,
                forecast.wind.speed.roundToInt()
            )
    }

    private fun rotationIconWind(forecast: Forecast) {
        Timber.d("rotationIconWind")

        bind.iconWindRouteIV.animate().rotation(forecast.wind.routeDegrees.toFloat())
    }

    private fun setDescription(forecast: Forecast) {
        Timber.d("setDescription")

        bind.descriptionTV.text = forecast.weather[0].description
    }

    private fun setIcon(forecast: Forecast) {
        Timber.d("setIcon")

        Glide.with(requireContext())
            .load(
                this.getString(
                    R.string.URL_loading_image_weather,
                    forecast.weather[0].sectionURL
                )
            )
            .placeholder(R.drawable.ic_cloud)
            .error(R.drawable.ic_no_internet)
            .into(bind.iconWeatherIV)
    }

    private fun setTemp(forecast: Forecast) {
        Timber.d("setTemp")

        bind.tempTV.text = round(forecast.main.temp).toInt().toString()
    }

    private fun setNameCity(forecast: Forecast) {
        Timber.d("setNameCity")

        bind.cityNameTV.text = this.getString(
            R.string.DetailsForecastFragment_text_name_city,
            forecast.cityName
        )
    }

    private fun setCountryCity(forecast: Forecast) {
        Timber.d("setCountryCity")

        bind.countryTV.text = this.getString(
            R.string.DetailsForecastFragment_text_country_city,
            forecast.sys.country
        )
    }

    private fun setTime(forecast: Forecast) {
        Timber.d("setTime")

        bind.dateTimeTV.text = this.getString(
            R.string.DetailsForecastFragment_updateText_text,
            forecast.timeForecast.convertToDate("dd MMM yyyy HH:mm")
        )
    }

    private fun getArgument(): Forecast? {
        Timber.d("getArgument")

        return requireArguments().getParcelable(ShortForecastListFragment.KEY)
    }

    private fun sendForecastInViewModel() {
        Timber.d("sendForecastInViewModel")

        detailsForecastViewModel.setDataDetailsForecastInView(
            getArgument()
                ?: error("CityInfoFragment / incorrect data in bundle")
        )
    }

    override fun onDestroy() {
        Timber.d("onDestroy")

        super.onDestroy()
    }
}

