package com.example.weatherapplication.presentation.models.forecast.details

import com.example.weatherapplication.presentation.models.city.UiCityDto

fun UiDetailsForecastDto.convertToUiCityDto(): UiCityDto {
    return UiCityDto(
        latitude = latitude,
        longitude = longitude,
        cityName = cityName,
        country = country
    )
}