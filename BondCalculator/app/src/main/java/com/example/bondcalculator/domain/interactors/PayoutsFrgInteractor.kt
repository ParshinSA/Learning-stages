package com.example.bondcalculator.domain.interactors

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPayoutsFrg
import io.reactivex.Single

interface PayoutsFrgInteractor {
    fun getDataForPayoutsFrg(): Single<DomainDataForPayoutsFrg>
}