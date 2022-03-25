package com.example.weatherapplication.data.data_source.interf.report

import com.example.weatherapplication.data.networking.models.report.request.RemoteRequestReportDto
import com.example.weatherapplication.data.networking.models.report.response.RemoteResponseReportDto
import io.reactivex.Observable

interface RemoteReportDataSource {

    fun requestReportToDay(
        remoteRequestReportDto: RemoteRequestReportDto
    ): Observable<RemoteResponseReportDto>

    fun requestReportToMonth(
        remoteRequestReportToMonthDto: RemoteRequestReportDto
    ): Observable<RemoteResponseReportDto>

}