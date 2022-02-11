package com.example.weatherapplication.data.retrofit.api

import com.example.weatherapplication.data.models.report.HistoryWeather
import com.example.weatherapplication.data.retrofit.ApiContract
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface HistoryApi {

    @GET("data/2.5/aggregated/month")
    fun requestHistoryMonth(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("month") month: Int,
        @Query("appid") apiKey: String = ApiContract.API_KEY
    ): Observable<HistoryWeather>

    @GET("data/2.5/aggregated/day")
    fun requestHistoryDay(
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("day") day: Int,
        @Query("month") month: Int,
        @Query("appid") apiKey: String = ApiContract.API_KEY
    ): Observable<HistoryWeather>

}
