package com.example.weatherapplication.presentation.models.forecast.short_forecast

import com.example.weatherapplication.domain.models.forecast.DomainForecast

fun DomainForecast.convertToUiShortForecast(): UiShortForecast {
    return UiShortForecast(
        latitude = latitude,
        longitude = longitude,
        cityName = cityName,
        temperature = temperature,
        iconId = iconId
    )
}
