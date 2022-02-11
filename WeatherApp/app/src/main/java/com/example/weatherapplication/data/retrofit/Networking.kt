package com.example.weatherapplication.data.retrofit

import com.example.weatherapplication.data.retrofit.api.CoordinationApi
import com.example.weatherapplication.data.retrofit.api.ForecastApi
import com.example.weatherapplication.data.retrofit.api.HistoryApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

// https://openweathermap.org/api

object Networking {

    private val retrofitForecast = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitHistory = Retrofit.Builder()
        .baseUrl("https://history.openweathermap.org/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitCoordination = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val forecastApi: ForecastApi = retrofitForecast.create()

    val historyApi: HistoryApi = retrofitHistory.create()

    val coordinationApi: CoordinationApi = retrofitCoordination.create()

}

