package com.example.weatherapplication.domain.repository

import com.example.weatherapplication.domain.models.report.DomainRequestReport
import com.example.weatherapplication.domain.models.report.DomainResponseReport
import com.example.weatherapplication.domain.models.report.DomainSaveReportString
import io.reactivex.Completable
import io.reactivex.Observable

interface ReportRepository {

    fun requestReportToDay(
        domainRequestReport: DomainRequestReport
    ): Observable<DomainResponseReport>

    fun requestReportToMonth(
        domainRequestReport: DomainRequestReport
    ): Observable<DomainResponseReport>

    fun saveInCache(domainSaveReportString: DomainSaveReportString): Completable

    fun openReportFromCache(): DomainSaveReportString

}