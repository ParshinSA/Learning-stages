package com.example.weatherapplication.presentation.models.report

import com.example.weatherapplication.presentation.models.report.nested_request_report.ReportingPeriod

data class UiRequestReport(
    val cityName: String,
    val latitude: Double,
    val longitude: Double,
    val reportingPeriod: ReportingPeriod
)
