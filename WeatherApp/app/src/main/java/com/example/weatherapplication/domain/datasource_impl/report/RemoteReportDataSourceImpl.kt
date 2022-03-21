package com.example.weatherapplication.domain.datasource_impl.report

import com.example.weatherapplication.data.data_source.report.RemoteReportDataSource
import com.example.weatherapplication.data.database.models.report.WeatherStatistic
import com.example.weatherapplication.data.networking.api.ReportApi
import io.reactivex.Observable
import javax.inject.Inject

class RemoteReportDataSourceImpl @Inject constructor(
    private val retrofitReportApi: ReportApi
) : RemoteReportDataSource {

    override fun requestReportToDay(
        latitude: Double,
        longitude: Double,
        day: Int,
        month: Int
    ): Observable<WeatherStatistic> {
        return retrofitReportApi.requestReportToDay(
            latitude = latitude,
            longitude = longitude,
            day = day,
            month = month
        )
    }

    override fun requestReportToMonth(
        latitude: Double,
        longitude: Double,
        month: Int
    ): Observable<WeatherStatistic> {
        return retrofitReportApi.requestReportToMonth(
            latitude = latitude,
            longitude = longitude,
            month = month
        )
    }
}