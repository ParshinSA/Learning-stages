package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.domain.interactors.SelectedDataInteractorImpl
import com.example.bondcalculator.domain.interactors.intf.SelectedDataInteractor
import dagger.Binds
import dagger.Module

@Module
interface InteractorModule {

    @Binds
    @Suppress("FunctionName")
    fun bindSecuritiesDataInteractorImpl_to_interface(interactor: SelectedDataInteractorImpl): SelectedDataInteractor
}