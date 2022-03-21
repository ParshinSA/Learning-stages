package com.example.weatherapplication.di.modules

import android.content.Context
import androidx.room.Room
import com.example.weatherapplication.data.database.custom_cities_db.CustomCitiesDao
import com.example.weatherapplication.data.database.custom_cities_db.CustomCitiesDbModels
import com.example.weatherapplication.data.database.forecast_db.ForecastDao
import com.example.weatherapplication.data.database.forecast_db.ForecastDbModels
import com.example.weatherapplication.presentation.common.contracts.CustomCitiesContract
import com.example.weatherapplication.presentation.common.contracts.ForecastDbContract
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideForecastDao(context: Context): ForecastDao {
        return Room.databaseBuilder(
            context,
            ForecastDbModels::class.java,
            ForecastDbContract.Database.NAME
        )
            .fallbackToDestructiveMigration()
            .build()
            .getForecastDao()
    }

    @Provides
    @Singleton
    fun provideCustomCitiesDao(context: Context): CustomCitiesDao {
        return Room.databaseBuilder(
            context,
            CustomCitiesDbModels::class.java,
            CustomCitiesContract.Database.NAME
        )
            .fallbackToDestructiveMigration()
            .build()
            .getCustomCitiesDao()
    }
}