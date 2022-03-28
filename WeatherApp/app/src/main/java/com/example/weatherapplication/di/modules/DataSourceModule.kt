package com.example.weatherapplication.di.modules

import com.example.weatherapplication.data.data_source.impl.custom_city.RemoteCityDataSourceImpl
import com.example.weatherapplication.data.data_source.impl.custom_city.RoomCityDataSourceImpl
import com.example.weatherapplication.data.data_source.impl.forecast.RemoteForecastDataSourceImpl
import com.example.weatherapplication.data.data_source.impl.forecast.RoomForecastDataSourceImpl
import com.example.weatherapplication.data.data_source.impl.report.MemoryReportDataSourceImpl
import com.example.weatherapplication.data.data_source.impl.report.RemoteReportDataSourceImpl
import com.example.weatherapplication.data.data_source.impl.shared_preference.SharedPreferenceDataSourceImpl
import com.example.weatherapplication.data.data_source.interf.custom_cities.RemoteCityDataSource
import com.example.weatherapplication.data.data_source.interf.custom_cities.RoomCityDataSource
import com.example.weatherapplication.data.data_source.interf.forecast.RemoteForecastDataSource
import com.example.weatherapplication.data.data_source.interf.forecast.RoomForecastDataSource
import com.example.weatherapplication.data.data_source.interf.report.MemoryReportDataSource
import com.example.weatherapplication.data.data_source.interf.report.RemoteReportDataSource
import com.example.weatherapplication.data.data_source.interf.shared_preference.SharedPreferenceDataSource
import dagger.Binds
import dagger.Module

@Module
interface DataSourceModule {

    @Suppress("FunctionName")
    @Binds
    fun bindRemoteCustomCityDataSourceImpl_to_RemoteCustomCityDataSource(
        dataSource: RemoteCityDataSourceImpl
    ): RemoteCityDataSource

    @Suppress("FunctionName")
    @Binds
    fun bindRoomCustomCitiesDataSourceImpl_to_RoomCustomCitiesDataSource(
        dataSource: RoomCityDataSourceImpl
    ): RoomCityDataSource

    @Suppress("FunctionName")
    @Binds
    fun bindRemoteForecastDataSourceImpl_to_RemoteForecastDataSource(
        dataSource: RemoteForecastDataSourceImpl
    ): RemoteForecastDataSource

    @Suppress("FunctionName")
    @Binds
    fun bindRoomForecastDataSourceImpl_to_RoomForecastDataSource(
        dataSource: RoomForecastDataSourceImpl
    ): RoomForecastDataSource

    @Suppress("FunctionName")
    @Binds
    fun bindRemoteReportDataSourceImpl_to_RemoteReportDataSource(
        dataSource: RemoteReportDataSourceImpl
    ): RemoteReportDataSource

    @Suppress("FunctionName")
    @Binds
    fun bindSharedPreferenceDataSourceImpl_to_SharedPreferenceDataSource(
        dataSource: SharedPreferenceDataSourceImpl
    ): SharedPreferenceDataSource

    @Suppress("FunctionName")
    @Binds
    fun bindMemoryReportDataSourceImpl_to_MemoryReportDataSource(
        dataSource: MemoryReportDataSourceImpl
    ): MemoryReportDataSource
}