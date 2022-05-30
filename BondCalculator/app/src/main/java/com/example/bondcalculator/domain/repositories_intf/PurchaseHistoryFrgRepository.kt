package com.example.bondcalculator.domain.repositories_intf

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPurchaseHistoryFrg
import io.reactivex.Single

interface PurchaseHistoryFrgRepository {
    fun getDataForPurchaseHistoryFrg(): Single<DomainDataForPurchaseHistoryFrg>
}