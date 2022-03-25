package com.example.weatherapplication.domain.models.user_input

import com.example.weatherapplication.data.networking.models.city.request.RemoteRequestSearchByCityNameDto
import com.example.weatherapplication.domain.models.city.request.DomainRequestSearchByCityNameDto

fun DomainRequestSearchByCityNameDto.convertToRemoteRequestSearchByCityNameDto(): RemoteRequestSearchByCityNameDto {
    return RemoteRequestSearchByCityNameDto(
        name = name
    )
}