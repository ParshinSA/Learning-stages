package com.example.weatherapplication.data.networking.api

import com.example.weatherapplication.data.database.models.city.City
import com.example.weatherapplication.presentation.common.contracts.NetworkContract
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchCityApi {

    @GET(value = "geo/1.0/direct")
    fun searchCityByName(
        @Query("q") cityName: String,
        @Query("limit") limit: Int = 3,
        @Query("appid") apiKey: String = NetworkContract.API_KEY
    ): Observable<List<City>>

}