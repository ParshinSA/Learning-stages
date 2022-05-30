package com.example.bondcalculator.domain.repositories_intf

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPayoutsFrg
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPortfolioFrg
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPurchaseHistoryFrg
import com.example.bondcalculator.domain.models.bonds_data.DomainBond
import com.example.bondcalculator.domain.models.bonds_data.DomainRequestBondList
import com.example.bondcalculator.domain.models.exchange_rate.DomainExchangeRateUsdToRub
import com.example.bondcalculator.domain.models.payment_calendar.DomainPaymentCalendar
import com.example.bondcalculator.domain.models.payment_calendar.DomainRequestPaymentCalendar
import io.reactivex.Observable
import io.reactivex.Single

interface SelectedFrgRepository {
    fun getExchangeRateUsdToRub(): Single<DomainExchangeRateUsdToRub>
    fun requestBondList(request: DomainRequestBondList): Single<List<DomainBond>>
    fun requestCouponInfo(request: DomainRequestPaymentCalendar): Single<DomainPaymentCalendar>
    fun saveDataForPortfolioFrg(data: DomainDataForPortfolioFrg)
    fun saveDataForPayoutsFrg(data: DomainDataForPayoutsFrg)
    fun saveDataForPurchaseHistoryFrg(data: DomainDataForPurchaseHistoryFrg)
}