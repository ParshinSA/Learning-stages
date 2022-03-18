package com.example.weatherapplication.interactors.forecast

import com.example.weatherapplication.data.reporitories.ForecastRepository

class GetForecasts(
    private val repository: ForecastRepository
) {

    fun execute() = repository.getForecasts()

}