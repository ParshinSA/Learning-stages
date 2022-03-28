package com.example.weatherapplication.data.networking.models.report.request

import com.example.weatherapplication.domain.models.report.DomainRequestReport

fun DomainRequestReport.convertToRemoteRequestReportDto(): RemoteRequestReportDto {
    return RemoteRequestReportDto(
        latitude = latitude,
        longitude = longitude,
        day = day,
        month = month
    )
}