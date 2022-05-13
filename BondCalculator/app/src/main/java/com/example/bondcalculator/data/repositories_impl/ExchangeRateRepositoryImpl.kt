package com.example.bondcalculator.data.repositories_impl

import com.example.bondcalculator.data.data_source.intf.RemoteExchangeRateDataDataSource
import com.example.bondcalculator.data.networking.models.toDomainExchangeRateUsdToRub
import com.example.bondcalculator.domain.models.exchange_rate.DomainExchangeRateUsdToRub
import com.example.bondcalculator.domain.repositories_intf.ExchangeRateRepository
import io.reactivex.Observable
import javax.inject.Inject

class ExchangeRateRepositoryImpl @Inject constructor(
    private val dataSource: RemoteExchangeRateDataDataSource
) : ExchangeRateRepository {

    override fun getExchangeRateUsdToRub(): Observable<DomainExchangeRateUsdToRub> {
        return dataSource.getExchangeRate()
            .map { it.toDomainExchangeRateUsdToRub() }
    }
}