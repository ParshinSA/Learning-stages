package com.example.weatherapplication.data.db.forecastdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapplication.data.models.forecast.Forecast

@Database(
    entities = [
        Forecast::class
    ],
    version = ForecastDbContract.Database.VERSION
)

abstract class ForecastDbModels : RoomDatabase() {
    abstract fun getForecastDao(): ForecastDao
}