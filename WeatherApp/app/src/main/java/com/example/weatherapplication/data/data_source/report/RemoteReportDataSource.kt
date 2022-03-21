package com.example.weatherapplication.data.data_source.report

import com.example.weatherapplication.data.database.models.report.WeatherStatistic
import io.reactivex.Observable

interface RemoteReportDataSource {

    fun requestReportToDay(
        latitude: Double,
        longitude: Double,
        day: Int,
        month: Int
    ): Observable<WeatherStatistic>

    fun requestReportToMonth(
        latitude: Double,
        longitude: Double,
        month: Int
    ): Observable<WeatherStatistic>

}