package com.example.weatherapplication.interactors.statistic

import com.example.weatherapplication.data.reporitories.StatisticalRepository

class RequestWeatherStatisticsStepDay(
    private val repository: StatisticalRepository,
) {

    fun requestStepDay(
        latitude: Double,
        longitude: Double,
        day: Int,
        month: Int
    ) {
        repository.requestForDay(
            latitude = latitude,
            longitude = longitude,
            day = day,
            month = month
        )
    }

}