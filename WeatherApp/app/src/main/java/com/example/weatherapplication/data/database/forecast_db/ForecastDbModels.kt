package com.example.weatherapplication.data.database.forecast_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapplication.data.database.models.forecast.Forecast
import com.example.weatherapplication.presentation.common.contracts.ForecastDbContract

@Database(
    entities = [
        Forecast::class
    ],
    version = ForecastDbContract.Database.VERSION
)

abstract class ForecastDbModels : RoomDatabase() {
    abstract fun getForecastDao(): ForecastDao
}