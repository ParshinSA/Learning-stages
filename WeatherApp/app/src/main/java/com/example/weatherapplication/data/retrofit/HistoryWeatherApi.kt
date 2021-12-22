package com.example.weatherapplication.data.retrofit

import com.example.weatherapplication.data.models.report.HistoryWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface HistoryWeatherApi {

    @GET("data/2.5/aggregated/month")
    suspend fun requestStatisticalMonthlyAggregation(
        @Query("id") cityId: Int,
        @Query("month") month: Int,
        @Query("appid") apiKey: String = ApiContract.API_KEY
    ): HistoryWeather

    @GET("data/2.5/aggregated/day")
    suspend fun requestStatisticalDailyAggregation(
        @Query("id") cityId: Int,
        @Query("month") month: Int,
        @Query("day") day: Int,
        @Query("appid") apiKey: String = ApiContract.API_KEY
    ): HistoryWeather

}
