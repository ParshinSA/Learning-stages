package com.example.weatherapplication.data.retrofit

import com.example.weatherapplication.data.models.forecast.Forecast
import retrofit2.http.GET
import retrofit2.http.Query


interface ForecastApi {

    @GET("data/2.5/weather")
    suspend fun requestWeatherForecastByCityId(
        @Query("id") cityId: Int,
        @Query("appid") apiKey: String = ApiContract.API_KEY,
        @Query("units") units: String = ApiContract.UNITS,
        @Query("lang") lang: String = ApiContract.LANG
    ): Forecast
}