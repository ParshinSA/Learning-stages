package com.example.bondcalculator.data.data_sourse.remote.impl

import com.example.bondcalculator.data.data_sourse.remote.intf.RemoteExchangeRateDataDataSource
import com.example.bondcalculator.data.networking.api.ExchangeRateApi
import com.example.bondcalculator.data.networking.models.exchange_rate.RemoteResponseExchangeRateDto
import io.reactivex.Single
import javax.inject.Inject

class RemoteExchangeRateDataDataSourceImpl @Inject constructor(
    private val exchangeRateApi: ExchangeRateApi
) : RemoteExchangeRateDataDataSource {

    override fun getExchangeRate(): Single<RemoteResponseExchangeRateDto> {
        return exchangeRateApi.getExchangeRate()
    }
}