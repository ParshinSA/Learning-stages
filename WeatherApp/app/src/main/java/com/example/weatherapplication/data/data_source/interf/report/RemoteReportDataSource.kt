package com.example.weatherapplication.data.data_source.interf.report

import com.example.weatherapplication.data.networking.models.report.RemoteRequestReportDto
import com.example.weatherapplication.data.networking.models.report.RemoteResponseReportDto
import io.reactivex.Observable

interface RemoteReportDataSource {

    fun requestReportToDay(
        remoteRequestReportDto: RemoteRequestReportDto
    ): Observable<RemoteResponseReportDto>

    fun requestReportToMonth(
        remoteRequestReportToMonthDto: RemoteRequestReportDto
    ): Observable<RemoteResponseReportDto>

}