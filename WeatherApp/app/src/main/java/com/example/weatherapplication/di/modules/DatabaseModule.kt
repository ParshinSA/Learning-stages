package com.example.weatherapplication.di.modules

import android.content.Context
import androidx.room.Room
import com.example.weatherapplication.data.database.description_db.city_db.CityDao
import com.example.weatherapplication.data.database.description_db.city_db.CityDbContract
import com.example.weatherapplication.data.database.description_db.city_db.CityDbModels
import com.example.weatherapplication.data.database.description_db.forecast_db.ForecastDao
import com.example.weatherapplication.data.database.description_db.forecast_db.ForecastDbContract
import com.example.weatherapplication.data.database.description_db.forecast_db.ForecastDbModels
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
    fun provideCustomCitiesDao(context: Context): CityDao {
        return Room.databaseBuilder(
            context,
            CityDbModels::class.java,
            CityDbContract.Database.NAME
        )
            .fallbackToDestructiveMigration()
            .build()
            .getCustomCitiesDao()
    }
}