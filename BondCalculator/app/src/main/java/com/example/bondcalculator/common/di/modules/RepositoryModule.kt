package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.data.repositories_impl.SelectedFrgRepositoryImpl
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

}