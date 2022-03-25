package com.example.weatherapplication.domain.repository

import com.example.weatherapplication.domain.models.report.DomainReportDto
import com.example.weatherapplication.domain.models.report.request.DomainRequestReportDto
import com.example.weatherapplication.domain.models.report.response.DomainResponseReportDto
import io.reactivex.Completable
import io.reactivex.Observable

interface ReportRepository {

    fun requestReportToDay(
        domainRequestReportDto: DomainRequestReportDto
    ): Observable<DomainResponseReportDto>

    fun requestReportToMonth(
        domainRequestReportToMonthDto: DomainRequestReportDto
    ): Observable<DomainResponseReportDto>

    fun saveInCache(domainReportDto: DomainReportDto): Completable

    fun openReportFromCache(): DomainReportDto

}