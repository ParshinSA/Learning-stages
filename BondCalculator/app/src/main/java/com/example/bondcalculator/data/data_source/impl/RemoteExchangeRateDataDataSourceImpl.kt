package com.example.bondcalculator.data.data_source.impl

import com.example.bondcalculator.data.data_source.intf.RemoteExchangeRateDataDataSource
import com.example.bondcalculator.data.networking.api.ExchangeRateApi
import com.example.bondcalculator.data.networking.models.exchange_rate.RemoteResponseExchangeRateDto
import io.reactivex.Observable
import javax.inject.Inject

class RemoteExchangeRateDataDataSourceImpl @Inject constructor(
    private val exchangeRateApi: ExchangeRateApi
) : RemoteExchangeRateDataDataSource {

    override fun getExchangeRate(): Observable<RemoteResponseExchangeRateDto> {
        return exchangeRateApi.getExchangeRate()
    }
}