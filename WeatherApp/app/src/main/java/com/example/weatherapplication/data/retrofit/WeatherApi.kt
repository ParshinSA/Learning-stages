package com.example.weatherapplication.data.retrofit

import com.example.weatherapplication.data.models.forecast.Forecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    fun requestWeatherForecast(
        @Query("id") queryCityId: Int,
        @Query("appid") apiKey: String = ForecastApiContract.API_KEY,
        @Query("units") units: String = ForecastApiContract.UNITS,
        @Query("lang") lang: String = ForecastApiContract.LANG
    ) : Call<Forecast>
}