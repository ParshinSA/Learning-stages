package com.example.weatherapplication.data.networking.models.forecast.request

import com.example.weatherapplication.domain.models.city.DomainCity

fun DomainCity.convertToRemoteRequestForecastDto(): RemoteRequestForecastDto {
    return RemoteRequestForecastDto(
        latitude = latitude,
        longitude = longitude
    )
}