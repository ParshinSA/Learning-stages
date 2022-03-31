package com.example.weatherapplication.data.networking.models.report

import com.example.weatherapplication.domain.models.report.DomainRequestReport
import com.example.weatherapplication.domain.models.report.DomainResponseReport

fun DomainRequestReport.convertToRemoteRequestReportDto(): RemoteRequestReportDto {
    return RemoteRequestReportDto(
        latitude = latitude,
        longitude = longitude,
        day = day,
        month = month
    )
}

fun RemoteResponseReportDto.convertToDomainResponseReport(): DomainResponseReport {
    return DomainResponseReport(
        temperature = reportData.temperature.medianValue,
        pressure = reportData.pressure.medianValue,
        humidity = reportData.humidity.medianValue,
        wind = reportData.wind.medianValue,
        precipitation = reportData.precipitation.medianValue
    )
}