package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.domain.interactors.*
import dagger.Binds
import dagger.Module

@Module
interface InteractorModule {

    @Binds
    @Suppress("FunctionName")
    fun bindSelectedFrgInteractorImpl_to_interface(
        interactor: SelectedFrgInteractorImpl
    ): SelectedFrgInteractor


    @Binds
    @Suppress("FunctionName")
    fun bindPortfolioFrgInteractorImpl_to_interface(
        interactor: PortfolioFrgInteractorImpl
    ): PortfolioFrgInteractor

    @Binds
    @Suppress("FunctionName")
    fun bindPayoutsFrgInteractorImpl_to_interface(
        interactor: PayoutsFrgInteractorImpl
    ): PayoutsFrgInteractor

    @Binds
    @Suppress("FunctionName")
    fun bindPurchaseHistoryInteractorImpl_to_interface(
        interactor: PurchaseHistoryFrgInteractorImpl
    ): PurchaseHistoryFrgInteractor
}