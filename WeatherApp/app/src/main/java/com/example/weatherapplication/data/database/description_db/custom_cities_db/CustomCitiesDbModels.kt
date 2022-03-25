package com.example.weatherapplication.data.database.description_db.custom_cities_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapplication.data.database.models.city.RoomCityDto

@Database(
    entities = [
        RoomCityDto::class
    ],
    version = CustomCitiesContract.Database.VERSION
)

abstract class CustomCitiesDbModels : RoomDatabase() {
    abstract fun getCustomCitiesDao(): CustomCitiesDao
}