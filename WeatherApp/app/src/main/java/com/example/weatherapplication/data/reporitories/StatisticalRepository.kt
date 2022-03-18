package com.example.weatherapplication.data.reporitories

import com.example.weatherapplication.app.framework.database.models.report.WeatherStatistic
import com.example.weatherapplication.data.data_source.statistical.CalculationOfAverageWeatherStatisticsDataSource
import com.example.weatherapplication.data.data_source.statistical.RemoteWeatherStatisticsDataSource
import javax.inject.Inject

class StatisticalRepository @Inject constructor(
    private val remoteStatisticalWeatherDataSource: RemoteWeatherStatisticsDataSource,
    private val calculationOfAverageWeatherStatisticsDataSource: CalculationOfAverageWeatherStatisticsDataSource
) {

    fun requestForDay(
        latitude: Double,
        longitude: Double,
        day: Int,
        month: Int
    ) {
        remoteStatisticalWeatherDataSource.requestDay(
            latitude = latitude,
            longitude = longitude,
            day = day,
            month = month
        )
    }

    fun requestForMonth(
        latitude: Double,
        longitude: Double,
        month: Int
    ) {
        remoteStatisticalWeatherDataSource.requestStepMonth(
            latitude = latitude,
            longitude = longitude,
            month = month
        )
    }

    fun calculate(listOfWeatherStatistics: List<WeatherStatistic>) {
        calculationOfAverageWeatherStatisticsDataSource.calculate(listOfWeatherStatistics)
    }

}