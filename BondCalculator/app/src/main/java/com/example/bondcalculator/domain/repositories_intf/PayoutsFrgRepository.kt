package com.example.bondcalculator.domain.repositories_intf

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPayoutsFrg
import io.reactivex.Single

interface PayoutsFrgRepository {

    fun getDataForPayoutsFrg(): Single<DomainDataForPayoutsFrg>
}
