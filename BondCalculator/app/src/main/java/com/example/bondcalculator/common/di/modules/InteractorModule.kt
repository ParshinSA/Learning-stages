package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.domain.interactors.SelectedFrgInteractor
import com.example.bondcalculator.domain.interactors.SelectedFrgInteractorImpl
import dagger.Binds
import dagger.Module

@Module
interface InteractorModule {

    @Binds
    @Suppress("FunctionName")
    fun bindCalculateYieldPortfolioInteractorImpl_to_interface(
        interactor: SelectedFrgInteractorImpl
    ): SelectedFrgInteractor
}