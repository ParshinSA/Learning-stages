package com.example.bondcalculator.domain.interactors

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForTextInfoDepositFrg
import io.reactivex.Single

interface TextInfoDepositFrgInteractor {
    fun getDataForTextInfoDepositFrg(): Single<DomainDataForTextInfoDepositFrg>
}