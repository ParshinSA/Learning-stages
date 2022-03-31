package com.example.weatherapplication.data.reporitory_impl

import com.example.weatherapplication.data.data_source.interf.report.MemoryReportDataSource
import com.example.weatherapplication.data.data_source.interf.report.RemoteReportDataSource
import com.example.weatherapplication.data.networking.models.report.convertToDomainResponseReport
import com.example.weatherapplication.data.networking.models.report.convertToRemoteRequestReportDto
import com.example.weatherapplication.domain.models.report.DomainRequestReport
import com.example.weatherapplication.domain.models.report.DomainResponseReport
import com.example.weatherapplication.domain.models.report.DomainSaveReportString
import com.example.weatherapplication.domain.repository.ReportRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val remoteReportDataSource: RemoteReportDataSource,
    private val memoryReportDataSource: MemoryReportDataSource
) : ReportRepository {

    override fun requestReportToDay(
        domainRequestReport: DomainRequestReport
    ): Observable<DomainResponseReport> {
        return remoteReportDataSource
            .requestReportToDay(
                domainRequestReport.convertToRemoteRequestReportDto()
            )
            .map { remoteResponseReportDto ->
                remoteResponseReportDto.convertToDomainResponseReport()
            }
    }

    override fun requestReportToMonth(
        domainRequestReport: DomainRequestReport
    ): Observable<DomainResponseReport> {
        return remoteReportDataSource
            .requestReportToMonth(
                domainRequestReport.convertToRemoteRequestReportDto()
            )
            .map { remoteResponseReportDto ->
                remoteResponseReportDto.convertToDomainResponseReport()
            }
    }

    override fun saveInCache(domainSaveReportString: DomainSaveReportString): Completable {
        return memoryReportDataSource.saveInCache(report = domainSaveReportString.reportString)
    }

    override fun openReportFromCache(): DomainSaveReportString {
        return DomainSaveReportString(
            reportString = memoryReportDataSource.openReportFromCache()
        )
    }

}