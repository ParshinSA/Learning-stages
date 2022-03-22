package com.example.weatherapplication.di.modules

import com.example.weatherapplication.data.data_source.custom_cities.RemoteCustomCityDataSource
import com.example.weatherapplication.data.data_source.custom_cities.RoomCustomCitiesDataSource
import com.example.weatherapplication.data.data_source.forecast.RemoteForecastDataSource
import com.example.weatherapplication.data.data_source.forecast.RoomForecastDataSource
import com.example.weatherapplication.data.data_source.report.MemoryReportDataSource
import com.example.weatherapplication.data.data_source.report.RemoteReportDataSource
import com.example.weatherapplication.data.data_source.shared_preference.SharedPreferenceDataSource
import com.example.weatherapplication.domain.datasource_impl.custom_city.RemoteCustomCityDataSourceImpl
import com.example.weatherapplication.domain.datasource_impl.custom_city.RoomCustomCitiesDataSourceImpl
import com.example.weatherapplication.domain.datasource_impl.forecast.RemoteForecastDataSourceImpl
import com.example.weatherapplication.domain.datasource_impl.forecast.RoomForecastDataSourceImpl
import com.example.weatherapplication.domain.datasource_impl.report.MemoryReportDataSourceImpl
import com.example.weatherapplication.domain.datasource_impl.report.RemoteReportDataSourceImpl
import com.example.weatherapplication.domain.datasource_impl.shared_preference.SharedPreferenceDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface DataSourceModule {

    @Suppress("FunctionName")
    @Binds
    fun bindRemoteCustomCityDataSourceImpl_to_RemoteCustomCityDataSource(
        dataSource: RemoteCustomCityDataSourceImpl
    ): RemoteCustomCityDataSource

    @Suppress("FunctionName")
    @Binds
    fun bindRoomCustomCitiesDataSourceImpl_to_RoomCustomCitiesDataSource(
        dataSource: RoomCustomCitiesDataSourceImpl
    ): RoomCustomCitiesDataSource

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