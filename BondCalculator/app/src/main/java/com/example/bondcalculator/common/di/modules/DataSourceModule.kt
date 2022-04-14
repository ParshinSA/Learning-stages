package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.data.data_source.impl.RequestSecuritiesDataDataSourceImpl
import com.example.bondcalculator.data.data_source.intf.RequestSecuritiesDataDataSource
import dagger.Binds
import dagger.Module

@Module
interface DataSourceModule {

    @Binds
    @Suppress("FunctionName")
    fun bindRequestSecuritiesDataDataSourceImpl_to_interface(
        dataSource: RequestSecuritiesDataDataSourceImpl
    ): RequestSecuritiesDataDataSource

}