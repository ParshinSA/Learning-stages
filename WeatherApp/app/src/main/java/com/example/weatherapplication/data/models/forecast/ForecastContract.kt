package com.example.weatherapplication.data.models.forecast

object ForecastContract {
    object TableDatabase {
        const val TABLE_NAME = "forecast_table"

        object Columns {
            const val PK_COORDINATION_CITY = "pk_coordination_city"
            const val WEATHER = "weather"
            const val MAIN = "main"
            const val VISIBILITY = "visibility"
            const val WIND = "wind"
            const val TIME_FORECAST = "time_forecast"
            const val CITY_NAME = "name"
            const val SYS = "sys"
        }
    }


    object GsonName {
        const val WEATHER = "weather"
        const val MAIN = "main"
        const val VISIBILITY = "visibility"
        const val WIND = "wind"
        const val TIME_FORECAST = "dt"
        const val CITY_NAME = "name"
        const val COORDINATION_CITY = "coord"
        const val SYS = "sys"
    }
}
