package com.example.exchanger.retrofitgson

import com.example.exchanger.currency.ObjectValute
import retrofit2.Call
import retrofit2.http.GET

interface APICourse {
    @GET("daily_json.js")
    fun getCourse(): Call<ObjectValute>
}