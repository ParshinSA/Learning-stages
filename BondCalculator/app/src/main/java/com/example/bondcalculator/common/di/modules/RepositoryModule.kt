package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.data.repositories_impl.*
import com.example.bondcalculator.domain.repositories_intf.*
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    @Suppress("FunctionName")
    fun bindSelectedFrgRepositoryImpl_to_Interface(
        repository: SelectedFrgRepositoryImpl
    ): SelectedFrgRepository

    @Binds
    @Suppress("FunctionName")
    fun bindPortfolioFrgRepositoryImpl_to_Interface(
        repository: PortfolioFrgRepositoryImpl
    ): PortfolioFrgRepository

    @Binds
    @Suppress("FunctionName")
    fun bindPayoutsFrgRepositoryImpl_to_interface(
        repository: PayoutsFrgRepositoryImpl
    ): PayoutsFrgRepository

    @Binds
    @Suppress("FunctionName")
    fun bindPurchaseHistoryFrgRepositoryImpl_to_interface(
        repository: PurchaseHistoryFrgRepositoryImpl
    ): PurchaseHistoryFrgRepository

    @Binds
    @Suppress("FunctionName")
    fun bindCompositionFrgRepositoryImpl_to_interface(
        interactor: CompositionFrgRepositoryImpl
    ): CompositionFrgRepository

    @Binds
    @Suppress("FunctionName")
    fun bindTextInfoDepositFrgRepositoryImpl_to_interface(
        interactor: TextInfoDepositFrgRepositoryImpl
    ): TextInfoDepositFrgRepository
}