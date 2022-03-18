package com.example.weatherapplication.interactors.statistic

import com.example.weatherapplication.data.reporitories.StatisticalRepository

class RequestWeatherStatisticsStepMonth(
    private val repository: StatisticalRepository
) {

    fun execute(
        latitude: Double,
        longitude: Double,
        month: Int
    ) {
        repository.requestForMonth(
            latitude = latitude,
            longitude = longitude,
            month = month
        )
    }

}