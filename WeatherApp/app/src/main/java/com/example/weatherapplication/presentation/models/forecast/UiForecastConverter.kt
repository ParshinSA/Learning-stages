package com.example.weatherapplication.presentation.models.forecast

import com.example.weatherapplication.domain.models.forecast.DomainForecast
import com.example.weatherapplication.presentation.common.convertToDate
import com.example.weatherapplication.presentation.models.city.UiCity
import kotlin.math.roundToInt

fun UiDetailsForecast.convertToUiCityDto(): UiCity {
    return UiCity(
        latitude = latitude,
        longitude = longitude,
        cityName = cityName,
        country = country
    )
}

fun DomainForecast.convertToUiDetailsForecast(): UiDetailsForecast {
    return UiDetailsForecast(
        latitude = latitude,
        longitude = longitude,
        temperature = temperature.toInt().toString(),
        pressure = pressure,
        humidity = humidity,
        description = description,
        iconId = iconId,
        visibility = visibility / 1000L,
        windSpeed = windSpeed.roundToInt(),
        windDirectionDegrees = windDirectionDegrees,
        cityName = cityName,
        country = country,
        sunrise = sunrise.convertToDate("HH:mm"),
        sunset = sunset.convertToDate("HH:mm"),
        forecastTime = forecastTime.convertToDate("dd MMM yyyy HH:mm")
    )
}

fun DomainForecast.convertToUiShortForecast(): UiShortForecast {
    return UiShortForecast(
        latitude = latitude,
        longitude = longitude,
        cityName = cityName,
        temperature = temperature,
        iconId = iconId
    )
}
