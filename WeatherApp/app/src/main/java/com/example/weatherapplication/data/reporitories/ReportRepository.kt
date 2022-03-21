package com.example.weatherapplication.data.reporitories

import com.example.weatherapplication.data.data_source.report.RemoteReportDataSource
import com.example.weatherapplication.data.database.models.report.WeatherStatistic
import io.reactivex.Observable
import javax.inject.Inject

class ReportRepository @Inject constructor(
    private val remoteReportDataSource: RemoteReportDataSource
) {

    fun requestReportToDay(
        latitude: Double,
        longitude: Double,
        day: Int,
        month: Int
    ): Observable<WeatherStatistic> {
        return remoteReportDataSource.requestReportToDay(
            latitude = latitude,
            longitude = longitude,
            day = day,
            month = month
        )
    }

    fun requestReportToMonth(
        latitude: Double,
        longitude: Double,
        month: Int
    ): Observable<WeatherStatistic> {
        return remoteReportDataSource.requestReportToMonth(
            latitude = latitude,
            longitude = longitude,
            month = month
        )
    }
}