package com.example.weatherapplication.data.database.models.city

import com.example.weatherapplication.domain.models.city.response.DomainCityDto

fun RoomCityDto.convertToDomainCityDto(): DomainCityDto {
    return DomainCityDto(
        name = name,
        latitude = latitude,
        longitude = longitude,
        country = country,
        state = state
    )
}