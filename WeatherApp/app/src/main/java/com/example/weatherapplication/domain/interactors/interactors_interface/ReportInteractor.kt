package com.example.weatherapplication.domain.interactors.interactors_interface

import com.example.weatherapplication.domain.models.report.DomainReportDto
import com.example.weatherapplication.presentation.models.report.request.UiRequestReportDto
import io.reactivex.Completable

interface ReportInteractor {

    fun generateReport(uiRequestReportDto: UiRequestReportDto): Completable

    fun openReportFromCache(): DomainReportDto

}
