package com.example.weatherapplication.domain.models.report

import com.example.weatherapplication.presentation.models.report.nested_request_report.ReportingPeriod

data class DomainRequestReport(
    val cityName: String = "", // заглушка
    val latitude: Double,
    val longitude: Double,
    val reportingPeriod: ReportingPeriod = ReportingPeriod.ONE_MONTH, // заглушка
    val day: Int = 0, // заглушка
    val month: Int = 0 // заглушка
)
