package com.example.weatherapplication.data.networking.models.city.request

import com.example.weatherapplication.domain.models.city.DomainRequestSearchByCityNameDto

fun DomainRequestSearchByCityNameDto.convertToRemoteRequestSearchByCityNameDto(): RemoteRequestSearchByCityNameDto {
    return RemoteRequestSearchByCityNameDto(
        name = name
    )
}