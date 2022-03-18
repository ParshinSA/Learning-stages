package com.example.weatherapplication.data.reporitories

import com.example.weatherapplication.data.data_source.forecast.RemoteForecastDataSource
import com.example.weatherapplication.data.data_source.forecast.RoomForecastDataSource
import com.example.weatherapplication.app.framework.database.models.forecast.Forecast
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val remoteForecastDataSource: RemoteForecastDataSource,
    private val roomForecastDataSource: RoomForecastDataSource
) {

    fun requestForecast(latitude: Double, longitude: Double) {
        remoteForecastDataSource.requestForecast(latitude, longitude)
    }

    fun saveForecast(forecast: Forecast) {
        roomForecastDataSource.addForecast(forecast = forecast)
    }

    fun getForecasts() = roomForecastDataSource.getForecasts()
}