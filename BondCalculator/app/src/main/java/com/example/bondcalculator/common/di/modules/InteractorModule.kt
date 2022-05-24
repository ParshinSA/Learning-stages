package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.domain.interactors.ChartsFrgInteractor
import com.example.bondcalculator.domain.interactors.ChartsFrgInteractorImpl
import com.example.bondcalculator.domain.interactors.SelectedFrgInteractor
import com.example.bondcalculator.domain.interactors.SelectedFrgInteractorImpl
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
    fun bindChartsFrgInteractorImpl_to_interface(
        interactor: ChartsFrgInteractorImpl
    ): ChartsFrgInteractor


}