package com.example.bondcalculator.domain.interactors

import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import com.example.bondcalculator.domain.repositories_intf.ChartsFrgRepository
import io.reactivex.Observable
import javax.inject.Inject

class ChartsFrgInteractorImpl @Inject constructor(
    private val repository: ChartsFrgRepository
) : ChartsFrgInteractor {

    override fun getCalculatePortfolioYield(): Observable<DomainPortfolioYield> {
        return repository.getCalculatePortfolioYield()
    }
}