package com.example.weatherapplication.data.repositories

import android.util.Log
import com.example.weatherapplication.data.db.appdb.AppDatabaseInit
import com.example.weatherapplication.data.models.forecast.Forecast

class DatabaseForecastRepository {

    private val forecastDao = AppDatabaseInit.instanceDatabaseModels.getForecastDao()

    suspend fun saveForecastInDatabase(forecast: Forecast) {
        forecastDao.insertListForecast(forecast)
    }

    suspend fun getForecastFromDatabase(cityId: Int): Forecast? {
        return forecastDao.getWeatherForecast(cityId).also {
        }
    }
}