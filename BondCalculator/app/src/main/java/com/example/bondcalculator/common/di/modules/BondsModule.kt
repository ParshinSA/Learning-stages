package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.domain.models.bonds_data.DomainBondFormulas
import com.example.bondcalculator.domain.models.bonds_data.DomainBondFormulasImpl
import dagger.Binds
import dagger.Module

@Module
sealed interface BondsModule {

    @Binds
    @Suppress("FunctionName")
    fun bindBondFormulasImpl_to_interface(bondFormulasImpl: DomainBondFormulasImpl): DomainBondFormulas
}