package com.example.weatherapplication.data.networking.models.forecast

import com.example.weatherapplication.domain.models.city.DomainCity
import com.example.weatherapplication.domain.models.forecast.DomainForecast

fun DomainCity.convertToRemoteRequestForecastDto(): RemoteRequestForecastDto {
    return RemoteRequestForecastDto(
        latitude = latitude,
        longitude = longitude
    )
}

fun RemoteResponseForecastDto.convertToDomainForecastDto(): DomainForecast {
    return DomainForecast(
        latitude = coordination.latitude,
        longitude = coordination.longitude,
        temperature = main.temperature,
        pressure = main.pressure,
        humidity = main.humidity,
        description = weather[0].description,
        iconId = weather[0].iconId,
        visibility = visibility,
        windSpeed = wind.speed,
        windDirectionDegrees = wind.routeDegrees,
        cityName = cityName,
        country = sys.country,
        sunrise = sys.sunrise,
        sunset = sys.sunset,
        forecastTime = forecastTime
    )
}