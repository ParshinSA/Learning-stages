package com.example.weatherapplication.domain.models.report.request

import com.example.weatherapplication.data.networking.models.report.request.RemoteRequestReportDto

fun DomainRequestReportDto.convertToRemoteRequestReportDto(): RemoteRequestReportDto {
    return RemoteRequestReportDto(
        latitude = latitude,
        longitude = longitude,
        day = day,
        month = month
    )
}