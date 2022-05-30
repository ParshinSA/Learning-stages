package com.example.bondcalculator.domain.interactors

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPayoutsFrg
import com.example.bondcalculator.domain.repositories_intf.PayoutsFrgRepository
import io.reactivex.Single
import javax.inject.Inject

class PayoutsFrgInteractorImpl @Inject constructor(
    private val repository: PayoutsFrgRepository
) : PayoutsFrgInteractor {

    override fun getDataForPayoutsFrg(): Single<DomainDataForPayoutsFrg> {
        return repository.getDataForPayoutsFrg()
    }
}