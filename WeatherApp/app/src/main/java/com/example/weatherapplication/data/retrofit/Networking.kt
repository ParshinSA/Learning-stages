package com.example.weatherapplication.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
// https://openweathermap.org/api

object Networking {

    private val retrofitForecast = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val forecastApi: ForecastApi = retrofitForecast.create()


    private val retrofitHistory = Retrofit.Builder()
        .baseUrl("https://history.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val historyApi: HistoryWeatherApi
        get() = retrofitHistory.create()
}

