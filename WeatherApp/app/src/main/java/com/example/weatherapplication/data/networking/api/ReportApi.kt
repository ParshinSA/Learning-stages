package com.example.weatherapplication.data.networking.api

import com.example.weatherapplication.data.database.models.report.WeatherStatistic
import com.example.weatherapplication.presentation.common.contracts.NetworkContract
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ReportApi {

    @GET("data/2.5/aggregated/month")
    fun requestReportToMonth(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("month") month: Int,
        @Query("appid") apiKey: String = NetworkContract.API_KEY
    ): Observable<WeatherStatistic>

    @GET("data/2.5/aggregated/day")
    fun requestReportToDay(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("day") day: Int,
        @Query("month") month: Int,
        @Query("appid") apiKey: String = NetworkContract.API_KEY
    ): Observable<WeatherStatistic>

}
