package com.example.weatherapplication.di.modules

import com.example.weatherapplication.data.networking.NetworkContract
import com.example.weatherapplication.data.networking.api.CoordinationApi
import com.example.weatherapplication.data.networking.api.ForecastApi
import com.example.weatherapplication.data.networking.api.HistoryApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideForecastApi(retrofit: Retrofit): ForecastApi {
        return retrofit
            .newBuilder()
            .baseUrl(NetworkContract.Url.FORECAST_API)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideHistoryApi(retrofit: Retrofit): HistoryApi {
        return retrofit
            .newBuilder()
            .baseUrl(NetworkContract.Url.HISTORY_API)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideCoordinationApi(retrofit: Retrofit): CoordinationApi {
        return retrofit
            .newBuilder()
            .baseUrl(NetworkContract.Url.COORDINATION_API)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}