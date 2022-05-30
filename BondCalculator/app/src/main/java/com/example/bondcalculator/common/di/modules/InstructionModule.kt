package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.domain.instruction.analysis_portfolio_yield.AnalysisPortfolioYield
import com.example.bondcalculator.domain.instruction.analysis_portfolio_yield.AnalysisPortfolioYieldImpl
import com.example.bondcalculator.domain.instruction.balance.Balance
import com.example.bondcalculator.domain.instruction.balance.BalanceImpl
import com.example.bondcalculator.domain.instruction.calculate_portfolio_yield.CalculatePortfolioYield
import com.example.bondcalculator.domain.instruction.calculate_portfolio_yield.CalculatePortfolioYieldImpl
import com.example.bondcalculator.domain.instruction.dond_formulas.BondFormulas
import com.example.bondcalculator.domain.instruction.dond_formulas.BondFormulasImpl
import com.example.bondcalculator.domain.instruction.download_progress.DownloadProgress
import com.example.bondcalculator.domain.instruction.download_progress.DownloadProgressImpl
import com.example.bondcalculator.domain.instruction.purchased_bonds.DomainPurchasedBonds
import com.example.bondcalculator.domain.instruction.purchased_bonds.DomainPurchasedBondsImpl
import dagger.Binds
import dagger.Module

@Module
interface InstructionModule {

    @Suppress("FunctionName")
    @Binds
    fun bindBalanceImpl_to_interface(
        balanceImpl: BalanceImpl
    ): Balance

    @Binds
    @Suppress("FunctionName")
    fun bindCalculatePortfolioYield_to_interface(
        calculateYieldImpl: CalculatePortfolioYieldImpl
    ): CalculatePortfolioYield

    @Suppress("FunctionName")
    @Binds
    fun bindsPurchasedBonds_to_interface(
        purchasedBonds: DomainPurchasedBondsImpl
    ): DomainPurchasedBonds

    @Binds
    @Suppress("FunctionName")
    fun bindBondFormulasImpl_to_interface(
        bondFormulasImpl: BondFormulasImpl
    ): BondFormulas

    @Binds
    @Suppress("FunctionName")
    fun bindProgressDataImpl_to_interface(
        downloadProgressImpl: DownloadProgressImpl
    ): DownloadProgress

    @Binds
    @Suppress("FunctionName")
    fun bindAnalysisPortfolioYield(
        analysisPortfolioYieldImpl: AnalysisPortfolioYieldImpl
    ): AnalysisPortfolioYield
}