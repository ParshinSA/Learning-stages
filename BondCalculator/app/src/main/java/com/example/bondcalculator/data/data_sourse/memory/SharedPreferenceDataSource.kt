package com.example.bondcalculator.data.data_sourse.memory

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.*
import io.reactivex.Single

interface SharedPreferenceDataSource {

    fun saveDataForPortfolioFrg(data: DomainDataForPortfolioFrg)
    fun getDataForPortfolioFrg(): Single<DomainDataForPortfolioFrg>

    fun saveDataForPayoutsFrg(data: DomainDataForPayoutsFrg)
    fun getDataForPayoutsFrg(): Single<DomainDataForPayoutsFrg>

    fun saveDataForPurchaseHistoryFrg(data: DomainDataForPurchaseHistoryFrg)
    fun getDataForPurchaseHistoryFrg(): Single<DomainDataForPurchaseHistoryFrg>

    fun saveDataForTextInfoDepositFrg(data: DomainDataForTextInfoDepositFrg)
    fun getDataForTextInfoDepositFrg(): Single<DomainDataForTextInfoDepositFrg>

    fun saveDataForCompositionFrg(data: DomainDataForCompositionFrg)
    fun getDataForCompositionFrg(): Single<DomainDataForCompositionFrg>
}
