package com.example.weatherapplication.domain.models.city

data class DomainCity(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String,
    val state: String
)