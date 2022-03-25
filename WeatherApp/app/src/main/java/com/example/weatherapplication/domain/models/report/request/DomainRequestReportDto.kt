package com.example.weatherapplication.domain.models.report.request

data class DomainRequestReportDto(
    val latitude: Double,
    val longitude: Double,
    val day: Int = 0,
    val month: Int
)
