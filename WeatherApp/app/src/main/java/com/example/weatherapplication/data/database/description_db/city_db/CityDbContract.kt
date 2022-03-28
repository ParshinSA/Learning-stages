package com.example.weatherapplication.data.database.description_db.city_db

object CityDbContract {
    object Database {
        const val VERSION = 1
        const val NAME = "custom_cities_data_base"

        object Columns {
            const val NAME = "name"
            const val LATITUDE = "latitude"
            const val LONGITUDE = "longitude"
            const val COUNTRY = "country"
            const val STATE = "state"
        }

    }

}