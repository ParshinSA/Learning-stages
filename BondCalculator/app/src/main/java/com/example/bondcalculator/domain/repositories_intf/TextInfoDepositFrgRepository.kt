package com.example.bondcalculator.domain.repositories_intf

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForTextInfoDepositFrg
import io.reactivex.Single

interface TextInfoDepositFrgRepository {
    fun getDataForTextInfoDepositFrg(): Single<DomainDataForTextInfoDepositFrg>
}