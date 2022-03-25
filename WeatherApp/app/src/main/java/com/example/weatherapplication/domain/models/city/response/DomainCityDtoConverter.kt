package com.example.weatherapplication.domain.models.city.response

import com.example.weatherapplication.data.database.models.city.RoomCityDto
import com.example.weatherapplication.data.networking.models.forecast.request.RemoteRequestForecastDto
import com.example.weatherapplication.domain.models.city.response.DomainCityDto


fun DomainCityDto.convertToRoomCityDto(): RoomCityDto {
    return RoomCityDto(
        name = name,
        latitude = latitude,
        longitude = longitude,
        country = country,
        state = state
    )
}

fun DomainCityDto.convertToRemoteRequestForecastDto(): RemoteRequestForecastDto {
    return RemoteRequestForecastDto(
        latitude = latitude,
        longitude = longitude
    )
}