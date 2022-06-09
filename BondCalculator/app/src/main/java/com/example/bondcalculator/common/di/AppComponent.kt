package com.example.bondcalculator.common.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.bondcalculator.common.di.modules.*
import com.example.bondcalculator.presentation.fragments.*
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
    fun inject(fragment: CompositionFragment)
    fun inject(fragment: TextInfoDepositFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}