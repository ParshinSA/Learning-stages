package com.example.myapplication.retrofitgson

import com.example.myapplication.currency.ObjectValute
import retrofit2.Call
import retrofit2.http.GET

interface APICourse {
    @GET("daily_json.js")
    fun getCourse(): Call<ObjectValute>
}