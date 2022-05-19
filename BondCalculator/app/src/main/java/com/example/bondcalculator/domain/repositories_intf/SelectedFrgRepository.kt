package com.example.bondcalculator.domain.repositories_intf

import com.example.bondcalculator.domain.models.bonds_data.DomainBond
import com.example.bondcalculator.domain.models.bonds_data.DomainRequestBondList
import com.example.bondcalculator.domain.models.exchange_rate.DomainExchangeRateUsdToRub
import com.example.bondcalculator.domain.models.payment_calendar.DomainPaymentCalendar
import com.example.bondcalculator.domain.models.payment_calendar.DomainRequestPaymentCalendar
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import io.reactivex.Observable

interface SelectedFrgRepository {
    fun getExchangeRateUsdToRub(): Observable<DomainExchangeRateUsdToRub>
    fun requestBondList(request: DomainRequestBondList): Observable<List<DomainBond>>
    fun requestCouponInfo(request: DomainRequestPaymentCalendar): Observable<DomainPaymentCalendar>
    fun getCalculate(): DomainPortfolioYield
    fun saveCalculate(calculate: DomainPortfolioYield)
}