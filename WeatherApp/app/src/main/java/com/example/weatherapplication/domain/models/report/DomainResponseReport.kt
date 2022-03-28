package com.example.weatherapplication.domain.models.report

data class DomainResponseReport(
    val temperature: Double,
    val pressure: Double,
    val humidity: Double,
    var wind: Double,
    val precipitation: Double
)