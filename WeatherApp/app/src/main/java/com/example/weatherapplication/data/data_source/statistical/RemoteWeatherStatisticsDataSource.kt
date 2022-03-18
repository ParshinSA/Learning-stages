package com.example.weatherapplication.data.data_source.statistical

import com.example.weatherapplication.app.framework.database.models.report.StatisticalWeather
import io.reactivex.Observable

interface RemoteWeatherStatisticsDataSource {

    fun requestDay(
        latitude: Double,
        longitude: Double,
        day: Int,
        month: Int
    ): Observable<StatisticalWeather>

    fun requestStepMonth(
        latitude: Double,
        longitude: Double,
        month: Int
    ): Observable<StatisticalWeather>

}