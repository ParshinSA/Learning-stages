package com.example.bondcalculator.domain.instruction.calculate_portfolio_yield

import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioSettings
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield
import io.reactivex.Observable

interface CalculatePortfolioYield {
    fun execute(settings: DomainPortfolioSettings): Observable<DomainPortfolioYield>
}