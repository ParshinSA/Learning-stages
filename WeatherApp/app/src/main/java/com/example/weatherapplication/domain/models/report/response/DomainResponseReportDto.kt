package com.example.weatherapplication.domain.models.report.response

data class DomainResponseReportDto(
    val temperature: Double,
    val pressure: Double,
    val humidity: Double,
    var wind: Double,
    val precipitation: Double
)