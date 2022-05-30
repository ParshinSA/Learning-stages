package com.example.bondcalculator.domain.interactors

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPurchaseHistoryFrg
import com.example.bondcalculator.domain.repositories_intf.PurchaseHistoryFrgRepository
import io.reactivex.Single
import javax.inject.Inject

class PurchaseHistoryFrgInteractorImpl @Inject constructor(
    private val repository: PurchaseHistoryFrgRepository
) : PurchaseHistoryFrgInteractor {

    override fun getDataForPurchaseHistoryFrg(): Single<DomainDataForPurchaseHistoryFrg> {
        return repository.getDataForPurchaseHistoryFrg()
    }
}