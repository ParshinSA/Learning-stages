package com.example.weatherapplication.data.repositories

import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.retrofit.Networking

class RemoteForecastRepository {
    private val databaseForecastRepository = DatabaseForecastRepository()

    suspend fun requestForecast(cityId: Int): Forecast {
        return try {
            Networking.forecastApi.requestWeatherForecastByCityId(cityId = cityId).also {
                databaseForecastRepository.saveForecastInDatabase(it)
            }
        } catch (t: Throwable) {
            error("error requestForecast $t")
        }
    }
}