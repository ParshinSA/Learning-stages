package com.example.weatherapplication.data.retrofit

import com.example.weatherapplication.data.retrofit.NetworkContract.Url
import com.example.weatherapplication.data.retrofit.api.CoordinationApi
import com.example.weatherapplication.data.retrofit.api.ForecastApi
import com.example.weatherapplication.data.retrofit.api.HistoryApi
import com.example.weatherapplication.utils.addAdapterAndConverterFactory
import retrofit2.Retrofit
import retrofit2.create

// https://openweathermap.org/api

object Networking {

    val forecastApi: ForecastApi = createRetrofitBuilder(Url.FORECAST_API).create()
    val historyApi: HistoryApi = createRetrofitBuilder(Url.HISTORY_API).create()
    val coordinationApi: CoordinationApi = createRetrofitBuilder(Url.COORDINATION_API).create()

    private fun createRetrofitBuilder(Url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Url)
            .addAdapterAndConverterFactory()
            .build()
    }
}

