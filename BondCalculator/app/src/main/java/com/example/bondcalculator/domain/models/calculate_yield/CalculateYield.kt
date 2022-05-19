package com.example.bondcalculator.domain.models.calculate_yield

import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioSettings
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import io.reactivex.Observable

interface CalculateYield {
    fun execute(settings: DomainPortfolioSettings): Observable<DomainPortfolioYield>
}