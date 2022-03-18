package com.example.weatherapplication.data.data_source.forecast

import com.example.weatherapplication.app.framework.database.models.forecast.Forecast
import io.reactivex.Observable

interface RemoteForecastDataSource {

    fun requestForecast(latitude: Double, longitude: Double): Observable<Forecast>

}