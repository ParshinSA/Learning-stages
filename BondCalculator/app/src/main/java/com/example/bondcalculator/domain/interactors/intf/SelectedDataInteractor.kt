package com.example.bondcalculator.domain.interactors.intf

import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar
import com.example.bondcalculator.domain.models.bonds_data.DomainRequestBondList
import com.example.bondcalculator.domain.models.exchange_rate.DomainExchangeRateUsdToRub
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioSettings
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import io.reactivex.Observable

interface SelectedDataInteractor {
    fun getExchangerRateUsdToRub(): Observable<DomainExchangeRateUsdToRub>
    fun getProfitableBonds(request: DomainRequestBondList): Observable<List<DomainBondAndCalendar>>
    fun calculateYieldPortfolio(portfolioSettings: DomainPortfolioSettings): Observable<DomainPortfolioYield>
}