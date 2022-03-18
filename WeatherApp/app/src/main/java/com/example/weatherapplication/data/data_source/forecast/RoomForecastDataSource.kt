package com.example.weatherapplication.data.data_source.forecast

import com.example.weatherapplication.app.framework.database.models.forecast.Forecast
import io.reactivex.Observable

interface RoomForecastDataSource {

    fun addForecast(forecast: Forecast)
    fun getForecasts(): Observable<List<Forecast>>

}
