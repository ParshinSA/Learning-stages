package com.example.weatherapplication.data.database.custom_cities_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapplication.data.database.models.city.City
import com.example.weatherapplication.presentation.common.contracts.CustomCitiesContract

@Database(
    entities = [
        City::class
    ],
    version = CustomCitiesContract.Database.VERSION
)

abstract class CustomCitiesDbModels : RoomDatabase() {
    abstract fun getCustomCitiesDao(): CustomCitiesDao
}