package com.example.weatherapplication.data.db.models.weather

object WeatherContract {
    const val TABLE_NAME = "weather_table"

    object Columns {
        const val PK_ID = "pk_auto_generate_id"
        const val WEATHER_ID = "weather_id"
        const val DESCRIPTION = "description"
        const val ICON_ID = "icon_id(section_URL)"
    }
}