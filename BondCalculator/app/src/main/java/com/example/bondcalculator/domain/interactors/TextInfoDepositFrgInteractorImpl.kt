package com.example.bondcalculator.domain.interactors

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForTextInfoDepositFrg
import com.example.bondcalculator.domain.repositories_intf.TextInfoDepositFrgRepository
import io.reactivex.Single
import javax.inject.Inject

class TextInfoDepositFrgInteractorImpl @Inject constructor(
    private val repository: TextInfoDepositFrgRepository
) : TextInfoDepositFrgInteractor {

    override fun getDataForTextInfoDepositFrg(): Single<DomainDataForTextInfoDepositFrg> {
        return repository.getDataForTextInfoDepositFrg()
    }
}