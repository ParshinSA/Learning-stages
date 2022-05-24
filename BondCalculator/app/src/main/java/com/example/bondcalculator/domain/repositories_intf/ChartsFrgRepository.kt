package com.example.bondcalculator.domain.repositories_intf

import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import io.reactivex.Observable

interface ChartsFrgRepository {
    fun getCalculatePortfolioYield(): Observable<DomainPortfolioYield>
}