package com.example.weatherapplication.app.framework.database.custom_cities_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapplication.app.framework.database.models.city.City

@Database(
    entities = [
        City::class
    ],
    version = CustomCitiesContract.Database.VERSION
)

abstract class CustomCitiesDbModels : RoomDatabase() {
    abstract fun getCustomCitiesDao(): CustomCitiesDao
}