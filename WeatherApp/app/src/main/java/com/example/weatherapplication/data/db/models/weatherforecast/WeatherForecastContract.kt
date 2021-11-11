package com.example.weatherapplication.data.db.models.weatherforecast

object WeatherForecastContract {
    const val TABLE_NAME = "weather_forecast_table"

    object Columns {
        const val PK_ID = "pk_auto_generate_id"
        const val FK_WEATHER_ID = "foreign_key_weather_id"
        const val FK_WIND_ID = "foreign_key_wind_id"
        const val FK_CLOUDS_ID = "foreign_key_clouds_id"
        const val FK_CITY_ID = "foreign_key_city_id"
        const val VISIBILITY = "visibility"
        const val TIME_FORECAST = "time forecast"
    }
}