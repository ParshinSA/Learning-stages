package com.example.weatherapplication.data.db.appdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapplication.data.db.appdb.dao.ForecastDao
import com.example.weatherapplication.data.models.forecast.Forecast

@Database(
    entities = [
        Forecast::class
    ],
    version = AppDatabaseContract.Database.VERSION
)

abstract class DatabaseModels : RoomDatabase() {
    abstract fun getForecastDao(): ForecastDao
}