package com.example.weatherapplication.data.retrofit.api

import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.retrofit.NetworkContract
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CoordinationApi {

    @GET(value = "geo/1.0/direct")
    fun getCoordinationByNameCity(
        @Query("q") userInput: String,
        @Query("limit") limit: Int = 1,
        @Query("appid") apiKey: String = NetworkContract.API_KEY
    ): Single<List<City>>

}