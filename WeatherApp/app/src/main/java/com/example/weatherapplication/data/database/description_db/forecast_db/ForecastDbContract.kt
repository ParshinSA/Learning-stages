package com.example.weatherapplication.data.database.description_db.forecast_db


object ForecastDbContract {
    object Database {
        const val VERSION = 1
        const val NAME = "forecast_data_base"

        object Columns {
            const val VISIBILITY = "Visibility"
            const val FORECAST_TIME = "Forecast time"
            const val CITY_NAME = "City name"
            const val LATITUDE = "Latitude"
            const val LONGITUDE = "Longitude"
            const val TEMPERATURE = "Temperature"
            const val PRESSURE = "Pressure"
            const val HUMIDITY = "Humidity"
            const val DESCRIPTION = "Description"
            const val ICON_ID = "Icon_ID"
            const val WIND_SPEED = "Wind speed"
            const val WIND_DIRECTION_DEGREES = "Wind direction (degrees)"
            const val COUNTRY = "Country"
            const val SUNRISE = "Sunrise"
            const val SUNSET = "Sunset"
        }

    }
}