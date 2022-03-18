package com.example.weatherapplication.data.networking.api

import com.example.weatherapplication.app.framework.database.models.forecast.Forecast
import com.example.weatherapplication.app.common.contracts.NetworkContract
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ForecastApi {

    @GET("data/2.5/weather")
    fun requestForecast(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("appid") apiKey: String = NetworkContract.API_KEY,
        @Query("units") units: String = NetworkContract.UNITS,
        @Query("lang") lang: String = NetworkContract.LANG
    ): Observable<Forecast>
}