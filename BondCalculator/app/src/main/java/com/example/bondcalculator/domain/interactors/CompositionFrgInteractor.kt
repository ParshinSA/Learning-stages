package com.example.bondcalculator.domain.interactors

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForCompositionFrg
import io.reactivex.Single

interface CompositionFrgInteractor {

    fun getDataForCompositionFrg(): Single<DomainDataForCompositionFrg>
}