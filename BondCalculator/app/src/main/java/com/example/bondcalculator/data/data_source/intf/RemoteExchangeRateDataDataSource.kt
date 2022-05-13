package com.example.bondcalculator.data.data_source.intf

import com.example.bondcalculator.data.networking.models.exchange_rate.RemoteResponseExchangeRateDto
import io.reactivex.Observable

interface RemoteExchangeRateDataDataSource {
    fun getExchangeRate(): Observable<RemoteResponseExchangeRateDto>
}