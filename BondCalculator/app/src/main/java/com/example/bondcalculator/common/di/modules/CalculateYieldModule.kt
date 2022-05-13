package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.domain.models.account.DomainBankAccount
import com.example.bondcalculator.domain.models.account.DomainBankAccountImpl
import com.example.bondcalculator.domain.models.balance.DomainBalance
import com.example.bondcalculator.domain.models.balance.DomainBalanceImpl
import com.example.bondcalculator.domain.models.calculate_yield_2.CalculateYield2
import com.example.bondcalculator.domain.models.calculate_yield_2.CalculateYield2Impl
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolio
import com.example.bondcalculator.domain.models.portfplio.DomainPortfolioImpl
import com.example.bondcalculator.domain.models.purchased_bonds.DomainPurchasedBonds
import com.example.bondcalculator.domain.models.purchased_bonds.DomainPurchasedBondsImpl
import dagger.Binds
import dagger.Module

@Module
interface CalculateYieldModule {

    @Suppress("FunctionName")
    @Binds
    fun bindBankAccountImpl_to_Interface(bankAccountImpl: DomainBankAccountImpl): DomainBankAccount

    @Suppress("FunctionName")
    @Binds
    fun bindBalanceImpl_to_Inteface(balanceImpl: DomainBalanceImpl): DomainBalance

    //    @Binds
//    @Suppress("FunctionName")
//    fun bindCalculateYield_to_Interface(calculateYieldImpl: DomainCalculateYieldImpl): DomainCalculateYield
    @Binds
    @Suppress("FunctionName")
    fun bindCalculateYield_to_Interface(calculateYieldImpl: CalculateYield2Impl): CalculateYield2

    @Suppress("FunctionName")
    @Binds
    fun bindsPortfolioImpl_to_Interface(portfolio: DomainPortfolioImpl): DomainPortfolio

    @Suppress("FunctionName")
    @Binds
    fun bindsPurchasedBonds_to_Interface(purchasedBonds: DomainPurchasedBondsImpl): DomainPurchasedBonds
}