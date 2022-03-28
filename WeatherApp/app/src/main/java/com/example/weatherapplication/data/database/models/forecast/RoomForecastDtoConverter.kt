package com.example.weatherapplication.data.database.models.forecast

import com.example.weatherapplication.domain.models.forecast.DomainForecast

fun DomainForecast.convertToRoomForecastDto(): RoomForecastDto {
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

fun RoomForecastDto.convertToDomainForecast(): DomainForecast {
    return DomainForecast(
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
