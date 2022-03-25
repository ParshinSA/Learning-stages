package com.example.weatherapplication.data.reporitories

import com.example.weatherapplication.data.data_source.interf.report.MemoryReportDataSource
import com.example.weatherapplication.data.data_source.interf.report.RemoteReportDataSource
import com.example.weatherapplication.data.networking.models.report.response.convertToDomainResponseReportDto
import com.example.weatherapplication.domain.models.report.DomainReportDto
import com.example.weatherapplication.domain.models.report.request.DomainRequestReportDto
import com.example.weatherapplication.domain.models.report.request.convertToRemoteRequestReportDto
import com.example.weatherapplication.domain.models.report.response.DomainResponseReportDto
import com.example.weatherapplication.domain.repository.ReportRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val remoteReportDataSource: RemoteReportDataSource,
    private val memoryReportDataSource: MemoryReportDataSource
) : ReportRepository {

    override fun requestReportToDay(
        domainRequestReportDto: DomainRequestReportDto
    ): Observable<DomainResponseReportDto> {
        return remoteReportDataSource
            .requestReportToDay(
                domainRequestReportDto.convertToRemoteRequestReportDto()
            )
            .map { remoteResponseReportDto ->
                remoteResponseReportDto.convertToDomainResponseReportDto()
            }
    }

    override fun requestReportToMonth(
        domainRequestReportToMonthDto: DomainRequestReportDto
    ): Observable<DomainResponseReportDto> {
        return remoteReportDataSource
            .requestReportToMonth(
                domainRequestReportToMonthDto.convertToRemoteRequestReportDto()
            )
            .map { remoteResponseReportDto ->
                remoteResponseReportDto.convertToDomainResponseReportDto()
            }
    }

    override fun saveInCache(domainReportDto: DomainReportDto): Completable {
        return memoryReportDataSource.saveInCache(report = domainReportDto.reportString)
    }

    override fun openReportFromCache(): DomainReportDto {
        return DomainReportDto(
            reportString = memoryReportDataSource.openReportFromCache()
        )
    }

}