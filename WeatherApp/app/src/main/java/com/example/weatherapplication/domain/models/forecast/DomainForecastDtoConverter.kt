package com.example.weatherapplication.domain.models.forecast

import com.example.weatherapplication.data.database.models.forecast.RoomForecastDto
import com.example.weatherapplication.presentation.common.convertToDate
import com.example.weatherapplication.presentation.models.forecast.details.UiDetailsForecastDto
import com.example.weatherapplication.presentation.models.forecast.short.UiShortForecastDto
import kotlin.math.round
import kotlin.math.roundToInt

fun DomainForecastDto.convertToRoomForecastDto(): RoomForecastDto {
    return RoomForecastDto(
        latitude = latitude,
        longitude = longitude,
        temperature = temperature,
        pressure = pressure,
        humidity = humidity,
        description = description,
        iconId = iconId,
        visibility = visibility,
        windSpeed = windSpeed,
        windDirectionDegrees = windDirectionDegrees,
        cityName = cityName,
        country = country,
        sunrise = sunrise,
        sunset = sunset,
        forecastTime = forecastTime
    )
}

fun DomainForecastDto.convertToUiShortForecastDto(): UiShortForecastDto {
    return UiShortForecastDto(
        cityName = cityName,
        temperature = temperature.roundToInt().toString(),
        iconId = iconId,
        latitude = latitude,
        longitude = longitude
    )
}

fun DomainForecastDto.convertToUiDetailsForecastDto(): UiDetailsForecastDto {
    return UiDetailsForecastDto(
        latitude = latitude,
        longitude = longitude,
        temperature = round(temperature).toInt().toString(),
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