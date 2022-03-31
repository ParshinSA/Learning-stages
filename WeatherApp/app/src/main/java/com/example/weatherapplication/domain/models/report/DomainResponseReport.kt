package com.example.weatherapplication.domain.models.report

data class DomainResponseReport(
    val temperature: Double,
    val pressure: Double,
    val humidity: Double,
    val wind: Double,
    val precipitation: Double
)