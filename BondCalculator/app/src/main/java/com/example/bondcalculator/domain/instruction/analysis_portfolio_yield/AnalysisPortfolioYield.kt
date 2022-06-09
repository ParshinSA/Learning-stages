package com.example.bondcalculator.domain.instruction.analysis_portfolio_yield

import com.example.bondcalculator.domain.models.analysis_portfolio_yield.*
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioYield

interface AnalysisPortfolioYield {
    fun analysisForPortfolioFrg(data: DomainPortfolioYield): DomainDataForPortfolioFrg
    fun analysisForPayoutsFrg(data: DomainPortfolioYield): DomainDataForPayoutsFrg
    fun analysisForPurchaseHistoryFrg(data: DomainPortfolioYield): DomainDataForPurchaseHistoryFrg
    fun analysisForTextInfoDepositFrg(data: DomainPortfolioYield): DomainDataForTextInfoDepositFrg
    fun analysisForCompositionFrg(data: DomainPortfolioYield): DomainDataForCompositionFrg
}