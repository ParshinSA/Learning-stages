package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.data.data_source.impl.RemoteBondDataDataSourceImpl
import com.example.bondcalculator.data.data_source.impl.RemoteExchangeRateDataDataSourceImpl
import com.example.bondcalculator.data.data_source.intf.RemoteBondDataDataSource
import com.example.bondcalculator.data.data_source.intf.RemoteExchangeRateDataDataSource
import dagger.Binds
import dagger.Module

@Module
interface DataSourceModule {

    @Binds
    @Suppress("FunctionName")
    fun bindRemoteBondDataDataSourceImpl_to_interface(
        dataSource: RemoteBondDataDataSourceImpl
    ): RemoteBondDataDataSource

    @Binds
    @Suppress("FunctionName")
    fun bindRemoteExchangeRateDataDataSourceImpl_to_interface(
        dataSource: RemoteExchangeRateDataDataSourceImpl
    ): RemoteExchangeRateDataDataSource
}