package com.example.weatherapplication.domain.interactors.interactors_interface

import com.example.weatherapplication.domain.ReportingPeriod
import io.reactivex.Completable

interface ReportInteractor {

    fun generateReport(
        cityName: String,
        latitude: Double,
        longitude: Double,
        reportingPeriod: ReportingPeriod
    ): Completable

    fun openReportFromCache(): String

}
