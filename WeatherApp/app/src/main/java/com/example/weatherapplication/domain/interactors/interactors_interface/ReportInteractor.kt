package com.example.weatherapplication.domain.interactors.interactors_interface

import com.example.weatherapplication.domain.models.report.DomainRequestReport
import com.example.weatherapplication.domain.models.report.DomainSaveReportString
import io.reactivex.Completable

interface ReportInteractor {

    fun generateReport(domainRequestReport: DomainRequestReport): Completable
    fun openReportFromCache(): DomainSaveReportString

}
