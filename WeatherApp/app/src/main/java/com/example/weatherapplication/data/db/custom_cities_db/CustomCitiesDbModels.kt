package com.example.weatherapplication.data.db.custom_cities_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapplication.data.models.city.City

@Database(
    entities = [
        City::class
    ],
    version = CustomCitiesContract.Database.VERSION
)

abstract class CustomCitiesDbModels : RoomDatabase() {
    abstract fun getCustomCitiesDao(): CustomCitiesDao
}