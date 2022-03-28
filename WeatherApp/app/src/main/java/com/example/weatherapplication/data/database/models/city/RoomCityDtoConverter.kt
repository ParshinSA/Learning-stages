package com.example.weatherapplication.data.database.models.city

import com.example.weatherapplication.domain.models.city.DomainCity

fun DomainCity.convertToRoomCityDto(): RoomCityDto {
    return RoomCityDto(
        name = name,
        latitude = latitude,
        longitude = longitude,
        country = country,
        state = state
    )
}

fun RoomCityDto.convertToDomainCity(): DomainCity {
    return DomainCity(
        name = name,
        latitude = latitude,
        longitude = longitude,
        country = country,
        state = state
    )
}
