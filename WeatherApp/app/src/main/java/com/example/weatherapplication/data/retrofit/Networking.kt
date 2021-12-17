package com.example.weatherapplication.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Networking {

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val forecastApi: ForecastApi
        get() = retrofit.create()
}

