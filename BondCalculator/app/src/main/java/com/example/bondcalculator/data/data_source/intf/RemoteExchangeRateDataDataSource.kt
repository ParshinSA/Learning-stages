package com.example.bondcalculator.data.data_source.intf

import com.example.bondcalculator.data.networking.models.exchange_rate.RemoteResponseExchangeRateDto
import io.reactivex.Observable
import io.reactivex.Single

interface RemoteExchangeRateDataDataSource {
    fun getExchangeRate(): Single<RemoteResponseExchangeRateDto>
}