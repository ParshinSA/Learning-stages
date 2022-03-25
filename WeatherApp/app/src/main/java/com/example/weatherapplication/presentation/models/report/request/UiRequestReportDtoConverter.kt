package com.example.weatherapplication.presentation.models.report.request

import com.example.weatherapplication.domain.models.report.request.DomainRequestReportDto

fun UiRequestReportDto.convertToDomainRequestReportDto(): DomainRequestReportDto {
    return DomainRequestReportDto(
        latitude = latitude,
        longitude = longitude,
        day = day,
        month = month
    )
}