package com.example.weatherapplication.data.database.description_db.city_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapplication.data.database.models.city.RoomCityDto

@Database(
    entities = [
        RoomCityDto::class
    ],
    version = CityDbContract.Database.VERSION
)

abstract class CityDbModels : RoomDatabase() {
    abstract fun getCustomCitiesDao(): CityDao
}