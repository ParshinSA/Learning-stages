package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.domain.models.balance.DomainBalance
import com.example.bondcalculator.domain.models.balance.DomainBalanceImpl
import com.example.bondcalculator.domain.models.calculate_yield.CalculateYield
import com.example.bondcalculator.domain.models.calculate_yield.CalculateYieldImpl
import com.example.bondcalculator.domain.models.purchased_bonds.DomainPurchasedBonds
import com.example.bondcalculator.domain.models.purchased_bonds.DomainPurchasedBondsImpl
import dagger.Binds
import dagger.Module

@Module
interface CalculateYieldModule {

    @Suppress("FunctionName")
    @Binds
    fun bindBalanceImpl_to_Inteface(balanceImpl: DomainBalanceImpl): DomainBalance

    @Binds
    @Suppress("FunctionName")
    fun bindCalculateYield_to_Interface(calculateYieldImpl: CalculateYieldImpl): CalculateYield

    @Suppress("FunctionName")
    @Binds
    fun bindsPurchasedBonds_to_Interface(purchasedBonds: DomainPurchasedBondsImpl): DomainPurchasedBonds
}