package com.example.weatherapplication.interactors.forecast

import com.example.weatherapplication.data.reporitories.ForecastRepository

class RequestForecast(
    private val repository: ForecastRepository
) {

    fun execute(latitude: Double, longitude: Double) {
        repository.requestForecast(latitude = latitude, longitude = longitude)
    }

}