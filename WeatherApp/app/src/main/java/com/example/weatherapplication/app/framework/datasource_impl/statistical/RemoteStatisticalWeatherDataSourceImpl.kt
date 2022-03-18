package com.example.weatherapplication.app.framework.datasource_impl.statistical

import com.example.weatherapplication.data.data_source.statistical.RemoteWeatherStatisticsDataSource
import com.example.weatherapplication.app.framework.database.models.report.StatisticalWeather
import com.example.weatherapplication.data.networking.api.StatisticalApi
import io.reactivex.Observable

class RemoteStatisticalWeatherDataSourceImpl(
    private val retrofitStatisticalApi: StatisticalApi
) : RemoteWeatherStatisticsDataSource {

    override fun requestDay(
        latitude: Double,
        longitude: Double,
        day: Int,
        month: Int
    ): Observable<StatisticalWeather> {
        return retrofitStatisticalApi.requestHistoryDay(
            latitude = latitude,
            longitude = longitude,
            day = day,
            month = month
        )
    }

    override fun requestStepMonth(
        latitude: Double,
        longitude: Double,
        month: Int
    ): Observable<StatisticalWeather> {
        return retrofitStatisticalApi.requestHistoryMonth(
            latitude = latitude,
            longitude = longitude,
            month = month
        )
    }
}