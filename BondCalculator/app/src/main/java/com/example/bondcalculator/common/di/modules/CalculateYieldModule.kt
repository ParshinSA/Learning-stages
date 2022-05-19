package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.domain.models.balance.DomainBalance
import com.example.bondcalculator.domain.models.balance.DomainBalanceImpl
import com.example.bondcalculator.domain.models.bonds_data.DomainBondFormulas
import com.example.bondcalculator.domain.models.bonds_data.DomainBondFormulasImpl
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
    fun bindBalanceImpl_to_interface(
        balanceImpl: DomainBalanceImpl
    ): DomainBalance

    @Binds
    @Suppress("FunctionName")
    fun bindCalculateYield_to_interface(
        calculateYieldImpl: CalculateYieldImpl
    ): CalculateYield

    @Suppress("FunctionName")
    @Binds
    fun bindsPurchasedBonds_to_interface(
        purchasedBonds: DomainPurchasedBondsImpl
    ): DomainPurchasedBonds

    @Binds
    @Suppress("FunctionName")
    fun bindBondFormulasImpl_to_interface(
        bondFormulasImpl: DomainBondFormulasImpl
    ): DomainBondFormulas
}