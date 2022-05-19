package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.data.networking.api.ExchangeRateApi
import com.example.bondcalculator.data.networking.api.SecuritiesDataApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
class NetworkModule {

    @Provides
    fun provideSecuritiesDataApi(retrofit: Retrofit): SecuritiesDataApi {
        return retrofit
            .newBuilder()
            .baseUrl("https://iss.moex.com/")
            .build()
            .create()
    }

    @Provides
    fun provideExchangeRateApi(retrofit: Retrofit): ExchangeRateApi {
        return retrofit
            .newBuilder()
            .baseUrl("https://www.cbr-xml-daily.ru/")
            .build()
            .create()
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://default.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}