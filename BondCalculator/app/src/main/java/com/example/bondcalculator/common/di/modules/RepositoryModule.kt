package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.data.repositories_impl.RequestSecuritiesDataRepositoryImpl
import com.example.bondcalculator.domain.repositories_intf.RequestSecuritiesDataRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    @Suppress("FunctionName")
    fun bindRequestSecuritiesDataRepositoryImpl_to_interface(
        repository: RequestSecuritiesDataRepositoryImpl
    ): RequestSecuritiesDataRepository
}