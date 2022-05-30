package com.example.bondcalculator.domain.interactors

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPurchaseHistoryFrg
import io.reactivex.Single

interface PurchaseHistoryFrgInteractor {
    fun getDataForPurchaseHistoryFrg(): Single<DomainDataForPurchaseHistoryFrg>
}