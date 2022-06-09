package com.example.bondcalculator.domain.repositories_intf

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForCompositionFrg
import io.reactivex.Single

interface CompositionFrgRepository {
    fun getDataForCompositionFrg(): Single<DomainDataForCompositionFrg>
}