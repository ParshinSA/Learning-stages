package com.example.weatherapplication.di.modules

import android.content.Context
import androidx.room.Room
import com.example.weatherapplication.data.db.custom_cities_db.CustomCitiesContract
import com.example.weatherapplication.data.db.custom_cities_db.CustomCitiesDao
import com.example.weatherapplication.data.db.custom_cities_db.CustomCitiesDbModels
import com.example.weatherapplication.data.db.forecast_db.ForecastDao
import com.example.weatherapplication.data.db.forecast_db.ForecastDbContract
import com.example.weatherapplication.data.db.forecast_db.ForecastDbModels
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    fun provideForecastDbModels(context: Context): ForecastDbModels {
        return Room.databaseBuilder(
            context,
            ForecastDbModels::class.java,
            ForecastDbContract.Database.NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideForecastDao(forecastDbModels: ForecastDbModels): ForecastDao {
        return forecastDbModels.getForecastDao()
    }

    @Provides
    fun provideCustomCitiesDbModels(context: Context): CustomCitiesDbModels{
        return Room.databaseBuilder(
            context,
            CustomCitiesDbModels::class.java,
            CustomCitiesContract.Database.NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCustomCitiesDao(customCitiesDbModels: CustomCitiesDbModels): CustomCitiesDao{
        return customCitiesDbModels.getCustomCitiesDao()
    }

}