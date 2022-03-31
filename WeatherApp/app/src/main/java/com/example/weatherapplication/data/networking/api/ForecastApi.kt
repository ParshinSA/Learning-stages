package com.example.weatherapplication.data.networking.api

import com.example.weatherapplication.data.networking.NetworkContract
import com.example.weatherapplication.data.networking.models.forecast.RemoteResponseForecastDto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ForecastApi {

    @GET("data/2.5/weather")
    fun requestForecast(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = NetworkContract.API_KEY,
        @Query("units") units: String = NetworkContract.UNITS,
        @Query("lang") language: String = NetworkContract.LANG
    ): Observable<RemoteResponseForecastDto>
}