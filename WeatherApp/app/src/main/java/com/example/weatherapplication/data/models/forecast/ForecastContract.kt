package com.example.weatherapplication.data.models.forecast

object ForecastContract {
    object DatabaseTable {
        const val TABLE_NAME = "forecast_table"

        object Columns {
            const val PK_CITY_ID = "pk_city_id"
            const val WEATHER = "weather"
            const val MAIN = "main"
            const val VISIBILITY = "visibility"
            const val WIND = "wind"
            const val CLOUDS = "clouds"
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
        const val CLOUDS = "clouds"
        const val TIME_FORECAST = "dt"
        const val CITY_NAME = "name"
        const val CITY_ID = "id"
        const val SYS = "sys"
        const val STATUS_CODE = "cod"
    }
}
