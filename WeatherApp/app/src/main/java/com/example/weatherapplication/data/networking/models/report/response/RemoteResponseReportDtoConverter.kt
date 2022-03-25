package com.example.weatherapplication.data.networking.models.report.response

import com.example.weatherapplication.domain.models.report.response.DomainResponseReportDto

fun RemoteResponseReportDto.convertToDomainResponseReportDto(): DomainResponseReportDto {
    return DomainResponseReportDto(
        temperature = reportData.temperature.medianValue,
        pressure = reportData.pressure.medianValue,
        humidity = reportData.humidity.medianValue,
        wind = reportData.wind.medianValue,
        precipitation = reportData.precipitation.medianValue
    )
}