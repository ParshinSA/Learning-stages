package com.example.weatherapplication.data.networking.models.report

data class RemoteRequestReportDto(
    val latitude: Double,
    val longitude: Double,
    val day: Int,
    val month: Int
)
