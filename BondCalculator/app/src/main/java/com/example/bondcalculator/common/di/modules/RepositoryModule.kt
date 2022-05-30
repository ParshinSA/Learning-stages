package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.data.repositories_impl.PayoutsFrgRepositoryImpl
import com.example.bondcalculator.data.repositories_impl.PortfolioFrgRepositoryImpl
import com.example.bondcalculator.data.repositories_impl.PurchaseHistoryFrgRepositoryImpl
import com.example.bondcalculator.data.repositories_impl.SelectedFrgRepositoryImpl
import com.example.bondcalculator.domain.repositories_intf.PayoutsFrgRepository
import com.example.bondcalculator.domain.repositories_intf.PortfolioFrgRepository
import com.example.bondcalculator.domain.repositories_intf.PurchaseHistoryFrgRepository
import com.example.bondcalculator.domain.repositories_intf.SelectedFrgRepository
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
}