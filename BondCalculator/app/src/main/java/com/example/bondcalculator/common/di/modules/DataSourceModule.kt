package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.data.data_sourse.remote.impl.RemoteBondDataDataSourceImpl
import com.example.bondcalculator.data.data_sourse.remote.impl.RemoteExchangeRateDataDataSourceImpl
import com.example.bondcalculator.data.data_sourse.remote.intf.RemoteBondDataDataSource
import com.example.bondcalculator.data.data_sourse.remote.intf.RemoteExchangeRateDataDataSource
import com.example.bondcalculator.data.data_sourse.memory.SharedPreferenceDataSource
import com.example.bondcalculator.data.data_sourse.memory.SharedPreferenceDataSourceImpl
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

    @Binds
    @Suppress("FunctionName")
    fun bindSharedPreferenceDataSourceImpl_to_interface(
        sharedPrefs: SharedPreferenceDataSourceImpl
    ): SharedPreferenceDataSource
}