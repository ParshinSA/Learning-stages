package com.example.weatherapplication.interactors.statistic

import com.example.weatherapplication.app.framework.database.models.report.WeatherStatistic
import com.example.weatherapplication.data.reporitories.StatisticalRepository

class CalculationOfAverageWeatherStatistics(
    private val repository: StatisticalRepository
) {

    fun calculate(listOfWeatherStatistics: List<WeatherStatistic>) {
        repository.calculate(listOfWeatherStatistics)
    }

}