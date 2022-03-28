package com.example.weatherapplication.data.networking.models.report.response

import com.example.weatherapplication.domain.models.report.DomainResponseReport

fun RemoteResponseReportDto.convertToDomainResponseReport(): DomainResponseReport {
    return DomainResponseReport(
        temperature = reportData.temperature.medianValue,
        pressure = reportData.pressure.medianValue,
        humidity = reportData.humidity.medianValue,
        wind = reportData.wind.medianValue,
        precipitation = reportData.precipitation.medianValue
    )
}