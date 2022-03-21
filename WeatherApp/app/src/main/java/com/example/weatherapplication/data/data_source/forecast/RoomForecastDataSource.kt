package com.example.weatherapplication.data.data_source.forecast

import com.example.weatherapplication.data.database.models.forecast.Forecast
import io.reactivex.Completable
import io.reactivex.Observable

interface RoomForecastDataSource {

    fun addForecast(forecast: Forecast): Completable
    fun subscribeToForecastDatabase(): Observable<List<Forecast>>

}
