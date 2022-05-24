package com.example.bondcalculator.common.di.modules

import com.example.bondcalculator.common.URL_CBR_RU
import com.example.bondcalculator.common.URL_MOEX_COM
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
            .baseUrl(URL_MOEX_COM)
            .build()
            .create()
    }

    @Provides
    fun provideExchangeRateApi(retrofit: Retrofit): ExchangeRateApi {
        return retrofit
            .newBuilder()
            .baseUrl(URL_CBR_RU)
            .build()
            .create()
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL_CBR_RU)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}