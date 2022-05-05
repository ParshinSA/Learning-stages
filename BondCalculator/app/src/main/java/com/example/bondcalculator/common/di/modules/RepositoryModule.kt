package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.data.repositories_impl.BondDataRepositoryImpl
import com.example.bondcalculator.data.repositories_impl.ExchangeRateRepositoryImpl
import com.example.bondcalculator.domain.repositories_intf.BondDataRepository
import com.example.bondcalculator.domain.repositories_intf.ExchangeRateRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    @Suppress("FunctionName")
    fun bindBondDataRepositoryImpl_to_Interface(
        repository: BondDataRepositoryImpl
    ): BondDataRepository

    @Binds
    @Suppress("FunctionName")
    fun bindExchangeRateDataRepository_to_Interface(
        repositoryImpl: ExchangeRateRepositoryImpl
    ): ExchangeRateRepository
}