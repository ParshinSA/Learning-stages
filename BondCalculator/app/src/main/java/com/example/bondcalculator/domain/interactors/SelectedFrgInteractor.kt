package com.example.bondcalculator.domain.interactors

import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar
import com.example.bondcalculator.domain.models.bonds_data.DomainRequestBondList
import com.example.bondcalculator.domain.models.exchange_rate.DomainExchangeRateUsdToRub
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioSettings
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import io.reactivex.Observable
import io.reactivex.Single

interface SelectedFrgInteractor {
    fun getExchangerRateUsdToRub(): Single<DomainExchangeRateUsdToRub>
    fun getProfitableBonds(request: DomainRequestBondList): Observable<List<DomainBondAndCalendar>>
    fun calculatePortfolioYield(portfolioSettings: DomainPortfolioSettings): Observable<DomainPortfolioYield>
    fun analysisPortfolioYield(data: DomainPortfolioYield)
}