package com.example.weatherapplication.data.networking.models.city.response

import com.example.weatherapplication.domain.models.city.DomainCity

fun RemoteResponseCityDto.convertToDomainCityDto(): DomainCity {
    return DomainCity(
        name = name,
        latitude = latitude,
        longitude = longitude,
        country = country,
        state = state
    )
}
