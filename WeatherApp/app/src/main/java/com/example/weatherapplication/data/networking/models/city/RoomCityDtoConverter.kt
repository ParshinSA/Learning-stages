package com.example.weatherapplication.data.networking.models.city

import com.example.weatherapplication.domain.models.city.DomainCity
import com.example.weatherapplication.domain.models.city.DomainRequestSearchByCityName

fun DomainRequestSearchByCityName.convertToRemoteRequestSearchByCityNameDto(): RemoteRequestSearchByCityNameDto {
    return RemoteRequestSearchByCityNameDto(
        name = name
    )
}

fun RemoteResponseCityDto.convertToDomainCityDto(): DomainCity {
    return DomainCity(
        name = name,
        latitude = latitude,
        longitude = longitude,
        country = country,
        state = state
    )
}
