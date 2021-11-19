package com.example.weatherapplication.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Networking {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https:/api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherApi: WeatherApi
        get() = retrofit.create()
}

