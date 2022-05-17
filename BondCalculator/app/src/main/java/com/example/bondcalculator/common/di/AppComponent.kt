package com.example.bondcalculator.common.di

import android.content.Context
import com.example.bondcalculator.common.di.modules.*
import com.example.bondcalculator.presentation.ui.selection.SelectionFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        InteractorModule::class,
        RepositoryModule::class,
        DataSourceModule::class,
        BondsModule::class,
        CalculateYieldModule::class
    ]
)
interface AppComponent {

    fun inject(fragment: SelectionFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}