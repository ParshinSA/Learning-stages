package com.example.myapplication.retrofitgson

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object NetworkRetrofit {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.cbr-xml-daily.ru/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val netTestV: APICourse
        get() = retrofit.create()
}