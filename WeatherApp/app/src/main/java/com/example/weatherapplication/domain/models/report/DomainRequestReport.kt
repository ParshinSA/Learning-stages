package com.example.weatherapplication.domain.models.report

import com.example.weatherapplication.presentation.models.report.ReportPeriod

data class DomainRequestReport(
    val cityName: String = "", // заглушка
    val latitude: Double,
    val longitude: Double,
    val reportPeriod: ReportPeriod = ReportPeriod.ONE_MONTH, // заглушка
    var day: Int = 0, // заглушка
    var month: Int = 0 // заглушка
)
