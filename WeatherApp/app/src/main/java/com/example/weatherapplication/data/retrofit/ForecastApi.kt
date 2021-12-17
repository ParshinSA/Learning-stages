package com.example.weatherapplication.data.retrofit

import com.example.weatherapplication.data.models.forecast.Forecast
import retrofit2.http.GET
import retrofit2.http.Query

// https://openweathermap.org/api

interface ForecastApi {

    @GET("https:/api.openweathermap.org/data/2.5/weather")
    suspend fun requestWeatherForecastByCityId(
        @Query("id") queryCityId: Int,
        @Query("appid") apiKey: String = ForecastApiContract.API_KEY,
        @Query("units") units: String = ForecastApiContract.UNITS,
        @Query("lang") lang: String = ForecastApiContract.LANG
    ): Forecast


    suspend fun requestStatisticalWeatherData()
}