package com.example.weatherapplication.presentation.models.report.request

import com.example.weatherapplication.domain.models.report.DomainRequestReport

fun UiRequestReport.convertToDomainRequestReport(): DomainRequestReport {
    return DomainRequestReport(
        cityName = cityName,
        latitude = latitude,
        longitude = longitude,
        reportPeriod = reportPeriod
    )
}