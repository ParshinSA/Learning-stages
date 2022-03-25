package com.example.weatherapplication.data.database.description_db.forecast_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapplication.data.database.models.forecast.RoomForecastDto

@Database(
    entities = [
        RoomForecastDto::class
    ],
    version = ForecastDbContract.Database.VERSION
)

abstract class ForecastDbModels : RoomDatabase() {
    abstract fun getForecastDao(): ForecastDao
}