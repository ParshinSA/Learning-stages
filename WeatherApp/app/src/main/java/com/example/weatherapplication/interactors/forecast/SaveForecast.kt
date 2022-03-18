package com.example.weatherapplication.interactors.forecast

import com.example.weatherapplication.app.framework.database.models.forecast.Forecast
import com.example.weatherapplication.data.reporitories.ForecastRepository

class SaveForecast(
    private val repository: ForecastRepository
) {

    fun execute(forecast: Forecast) {
        repository.saveForecast(forecast = forecast)
    }

}