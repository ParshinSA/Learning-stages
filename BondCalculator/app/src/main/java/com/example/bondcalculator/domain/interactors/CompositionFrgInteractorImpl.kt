package com.example.bondcalculator.domain.interactors

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForCompositionFrg
import com.example.bondcalculator.domain.repositories_intf.CompositionFrgRepository
import io.reactivex.Single
import javax.inject.Inject

class CompositionFrgInteractorImpl @Inject constructor(
    private val repository: CompositionFrgRepository
) : CompositionFrgInteractor {

    override fun getDataForCompositionFrg(): Single<DomainDataForCompositionFrg> {
        return repository.getDataForCompositionFrg()
    }
}