package com.example.bondcalculator.domain.models.calculate_yield

import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioSettings
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import io.reactivex.Observable

interface DomainCalculateYield {

    fun calculatePortfolioYield(portfolioSettings: DomainPortfolioSettings): Observable<DomainPortfolioYield>
}