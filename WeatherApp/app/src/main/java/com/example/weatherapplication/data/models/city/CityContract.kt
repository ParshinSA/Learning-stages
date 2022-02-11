package com.example.weatherapplication.data.models.city

object CityContract {

    object TableDatabase {
        const val TABLE_NAME = "users_cities"

        object Columns {
            const val NAME = "name"
            const val LATITUDE = "latitude"
            const val LONGITUDE = "longitude"
            const val COUNTRY = "country"
            const val STATE = "state"
        }
    }

    object GsonName {
        const val NAME = "name"
        const val LATITUDE = "lat"
        const val LONGITUDE = "lon"
        const val COUNTRY = "country"
        const val STATE = "state"
    }
}
