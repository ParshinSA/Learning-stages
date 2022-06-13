package com.example.bondcalculator.domain.interactors

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPortfolioFrg
import io.reactivex.Single

interface PortfolioFrgInteractor {

    fun getDataForPortfolioFrg(): Single<DomainDataForPortfolioFrg>
}