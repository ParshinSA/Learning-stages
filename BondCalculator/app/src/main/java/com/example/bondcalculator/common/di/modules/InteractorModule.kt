package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.domain.interactors.SecuritiesDataInteractorImpl
import com.example.bondcalculator.domain.interactors.intf.SecuritiesDataInteractor
import dagger.Binds
import dagger.Module

@Module
interface InteractorModule {

    @Binds
    @Suppress("FunctionName")
    fun bindSecuritiesDataInteractorImpl_to_interface(
        interactor: SecuritiesDataInteractorImpl
    ): SecuritiesDataInteractor
}