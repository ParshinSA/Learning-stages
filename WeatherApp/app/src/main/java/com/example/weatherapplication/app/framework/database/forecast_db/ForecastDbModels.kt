package com.example.weatherapplication.app.framework.database.forecast_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapplication.app.framework.database.models.forecast.Forecast

@Database(
    entities = [
        Forecast::class
    ],
    version = ForecastDbContract.Database.VERSION
)

abstract class ForecastDbModels : RoomDatabase() {
    abstract fun getForecastDao(): ForecastDao
}