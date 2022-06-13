package com.example.bondcalculator.data.networking.api

import com.example.bondcalculator.data.networking.models.exchange_rate.RemoteResponseExchangeRateDto
import io.reactivex.Single
import retrofit2.http.GET

interface ExchangeRateApi {

    @GET("daily_json.js")
    fun getExchangeRate(): Single<RemoteResponseExchangeRateDto>
}