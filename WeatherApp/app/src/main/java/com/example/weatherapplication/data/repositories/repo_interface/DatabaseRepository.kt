package com.example.weatherapplication.data.repositories.repo_interface

import com.example.weatherapplication.data.models.forecast.Forecast
import io.reactivex.Flowable

interface DatabaseRepository {
    fun saveForecastInDatabase(forecast: Forecast)
    fun observeForecastDatabase(): Flowable<List<Forecast>>
}
