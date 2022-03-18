package com.example.weatherapplication.data.networking.api

import com.example.weatherapplication.app.framework.database.models.report.StatisticalWeather
import com.example.weatherapplication.app.common.contracts.NetworkContract
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface StatisticalApi {

    @GET("data/2.5/aggregated/month")
    fun requestHistoryMonth(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("month") month: Int,
        @Query("appid") apiKey: String = NetworkContract.API_KEY
    ): Observable<StatisticalWeather>

    @GET("data/2.5/aggregated/day")
    fun requestHistoryDay(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("day") day: Int,
        @Query("month") month: Int,
        @Query("appid") apiKey: String = NetworkContract.API_KEY
    ): Observable<StatisticalWeather>

}
