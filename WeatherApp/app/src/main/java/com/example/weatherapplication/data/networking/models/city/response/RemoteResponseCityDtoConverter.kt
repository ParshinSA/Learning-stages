package com.example.weatherapplication.data.networking.models.city.response

import com.example.weatherapplication.domain.models.city.response.DomainCityDto

fun RemoteResponseCityDto.convertToDomainCityDto(): DomainCityDto {
    return DomainCityDto(
        name = name,
        latitude = latitude,
        longitude = longitude,
        country = country,
        state = state
    )
}
