package com.example.weatherapplication.data.data_source.forecast

import com.example.weatherapplication.data.database.models.forecast.Forecast
import io.reactivex.Observable
import io.reactivex.Single

interface RemoteForecastDataSource {

    fun requestForecast(latitude: Double, longitude: Double): Observable<Forecast>

}