package com.example.bondcalculator.domain.repositories_intf

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPortfolioFrg
import io.reactivex.Single

interface PortfolioFrgRepository {
    fun getDataForPortfolioFrg(): Single<DomainDataForPortfolioFrg>
}