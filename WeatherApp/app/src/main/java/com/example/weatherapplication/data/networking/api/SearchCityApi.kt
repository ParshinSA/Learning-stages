package com.example.weatherapplication.data.networking.api

import com.example.weatherapplication.data.networking.NetworkContract
import com.example.weatherapplication.data.networking.models.city.RemoteResponseCityDto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchCityApi {

    @GET(value = "geo/1.0/direct")
    fun searchCityByName(
        @Query("q") name: String,
        @Query("limit") limit: Int = NetworkContract.LIMIT_VARIANTS,
        @Query("appid") apiKey: String = NetworkContract.API_KEY
    ): Observable<List<RemoteResponseCityDto>>

}