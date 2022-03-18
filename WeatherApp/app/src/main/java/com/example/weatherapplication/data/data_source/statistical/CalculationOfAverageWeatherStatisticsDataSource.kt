package com.example.weatherapplication.data.data_source.statistical

import com.example.weatherapplication.app.framework.database.models.report.WeatherStatistic
import com.example.weatherapplication.domain.ReportingPeriod

interface CalculationOfAverageWeatherStatisticsDataSource {

    fun calculate(
        listOfWeatherStatistics: List<WeatherStatistic>,
        period: ReportingPeriod
    ): WeatherStatistic

}