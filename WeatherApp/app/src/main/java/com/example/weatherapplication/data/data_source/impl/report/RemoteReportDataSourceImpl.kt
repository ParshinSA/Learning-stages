package com.example.weatherapplication.data.data_source.impl.report

import com.example.weatherapplication.data.data_source.interf.report.RemoteReportDataSource
import com.example.weatherapplication.data.networking.api.ReportApi
import com.example.weatherapplication.data.networking.models.report.request.RemoteRequestReportDto
import com.example.weatherapplication.data.networking.models.report.response.RemoteResponseReportDto
import io.reactivex.Observable
import javax.inject.Inject

class RemoteReportDataSourceImpl @Inject constructor(
    private val retrofitReportApi: ReportApi
) : RemoteReportDataSource {

    override fun requestReportToDay(
        remoteRequestReportDto: RemoteRequestReportDto
    ): Observable<RemoteResponseReportDto> {
        return retrofitReportApi.requestReportToDay(
            latitude = remoteRequestReportDto.latitude,
            longitude = remoteRequestReportDto.longitude,
            day = remoteRequestReportDto.day,
            month = remoteRequestReportDto.month
        )
    }

    override fun requestReportToMonth(
        remoteRequestReportToMonthDto: RemoteRequestReportDto
    ): Observable<RemoteResponseReportDto> {
        return retrofitReportApi.requestReportToMonth(
            latitude = remoteRequestReportToMonthDto.latitude,
            longitude = remoteRequestReportToMonthDto.longitude,
            month = remoteRequestReportToMonthDto.month
        )
    }
}