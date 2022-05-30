package com.example.bondcalculator.common.di

import android.content.Context
import com.example.bondcalculator.common.di.modules.*
import com.example.bondcalculator.presentation.ui.charts.ChartsFragment
import com.example.bondcalculator.presentation.ui.charts.nested.fragments.PayoutsFragment
import com.example.bondcalculator.presentation.ui.charts.nested.fragments.PortfolioFragment
import com.example.bondcalculator.presentation.ui.charts.nested.fragments.PurchaseHistoryFragment
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
        InstructionModule::class,
        MemoryModule::class
    ]
)
interface AppComponent {

    fun inject(fragment: SelectionFragment)
    fun inject(fragment: ChartsFragment)
    fun inject(fragment: PortfolioFragment)
    fun inject(fragment: PayoutsFragment)
    fun inject(fragment: PurchaseHistoryFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}