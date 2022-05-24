package com.example.bondcalculator.domain.interactors

import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import io.reactivex.Observable

interface ChartsFrgInteractor {

    fun getCalculatePortfolioYield(): Observable<DomainPortfolioYield>
}
