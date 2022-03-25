package com.example.weatherapplication.data.networking.models.forecast.response

import com.example.weatherapplication.domain.models.forecast.DomainForecastDto

fun RemoteResponseForecastDto.convertToDomainForecastDto(): DomainForecastDto {
    return DomainForecastDto(
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

