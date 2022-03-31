package com.example.weatherapplication.presentation.models.report

import com.example.weatherapplication.domain.models.report.DomainRequestReport

fun UiRequestReport.convertToDomainRequestReport(): DomainRequestReport {
    return DomainRequestReport(
        cityName = cityName,
        latitude = latitude,
        longitude = longitude,
        reportingPeriod = reportingPeriod
    )
}