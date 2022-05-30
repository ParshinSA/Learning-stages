package com.example.bondcalculator.data.memory

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPayoutsFrg
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPortfolioFrg
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPurchaseHistoryFrg
import io.reactivex.Single

interface SharedPreferenceDataSource {
    fun saveDataForPortfolioFrg(data: DomainDataForPortfolioFrg)
    fun getDataForPortfolioFrg(): Single<DomainDataForPortfolioFrg>
    fun saveDataForPayoutsFrg(data: DomainDataForPayoutsFrg)
    fun getDataForPayoutsFrg(): Single<DomainDataForPayoutsFrg>
    fun saveDataForPurchaseHistoryFrg(data: DomainDataForPurchaseHistoryFrg)
    fun getDataForPurchaseHistoryFrg(): Single<DomainDataForPurchaseHistoryFrg>
}
