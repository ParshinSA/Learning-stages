package com.example.weatherapplication.domain.models.city.response

data class DomainCityDto(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String,
    val state: String
)