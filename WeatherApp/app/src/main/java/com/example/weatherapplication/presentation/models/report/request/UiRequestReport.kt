package com.example.weatherapplication.presentation.models.report.request

import com.example.weatherapplication.presentation.models.report.ReportPeriod

data class UiRequestReport(
    val cityName: String,
    val latitude: Double,
    val longitude: Double,
    val reportPeriod: ReportPeriod
)
