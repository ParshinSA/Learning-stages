package com.example.bondcalculator.domain.repositories_intf

import com.example.bondcalculator.domain.models.exchange_rate.DomainExchangeRateUsdToRub
import io.reactivex.Observable

interface ExchangeRateRepository {
    fun getExchangeRateUsdToRub(): Observable<DomainExchangeRateUsdToRub>
}