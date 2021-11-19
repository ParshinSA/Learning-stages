package com.example.weatherapplication.ui.weather.cityinfo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.weatherapplication.R
import com.example.weatherapplication.app.convertToDate
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.databinding.FragmentCityInfoBinding
import com.example.weatherapplication.ui.weather.listofcity.ListOfCityFragment
import kotlin.math.round


class CityInfoFragment : Fragment(R.layout.fragment_city_info) {

    private var _bind: FragmentCityInfoBinding? = null
    private val bind: FragmentCityInfoBinding
        get() = _bind!!

    private lateinit var args: Forecast

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("SystemLogging", "CityInfoFragment / onCreateView")

        _bind = FragmentCityInfoBinding.inflate(inflater, container, false)
        action()
        return bind.root
    }

    private fun action() {
        getArgument()
        bindViewItems()
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

    private fun setTimeSunriseAndSunset() {
        bind.infoTextSunriseTV.text = "${args.sys.sunrise.convertToDate("HH:mm")}"
        bind.infoTextSunsetTV.text = "${args.sys.sunset.convertToDate("HH:mm")}"
    }

    private fun setVisibility() {
        bind.infoTextVisibilityTV.text = "${args.visibility / 1000L}km"
    }

    private fun setHumidity() {
        bind.infoTextHumidityTV.text = "${args.main.humidity}%"
    }

    private fun setPressure() {
        bind.infoTextPressureTV.text = "${args.main.pressure}hPa"
    }

    private fun setWind() {
        rotationIconWind()
        setInfoTextWind()
    }

    private fun setInfoTextWind() {
        bind.infoTextWindTV.text = "${args.wind.speed}m/s"
    }

    private fun rotationIconWind() {
        bind.iconWindRouteIV.animate().rotation(args.wind.routeDegrees.toFloat())
    }

    private fun setDescription() {
        bind.descriptionTV.text = args.weather[0].description
    }

    private fun setIcon() {
        Glide.with(requireContext())
            .load("https://openweathermap.org/img/wn/${args.weather[0].sectionURL.toString()}@2x.png")
            .placeholder(R.drawable.ic_cloud)
            .error(R.drawable.ic_no_internet)
            .into(bind.iconWeatherIV)
    }

    private fun setTemp() {
        bind.tempTV.text = round(args.main.temp).toInt().toString()
    }

    private fun setNameCity() {
        bind.cityNameTV.text = "${args.cityName}, ${args.sys.country}"
    }

    private fun setTime() {
        bind.dateTimeTV.text = "Обновленно: ${args.timeForecast.convertToDate("dd MMM yyyy HH:mm")}"
    }

    private fun getArgument() {
        args = requireArguments().getParcelable(ListOfCityFragment.KEY)
            ?: error("CityInfoFragment / incorrect data in bundle $args")
    }
}

