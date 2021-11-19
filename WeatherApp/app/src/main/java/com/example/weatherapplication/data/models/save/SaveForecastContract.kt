package com.example.weatherapplication.data.models.save

object SaveForecastContract {
    const val TABLE_NAME = "weather_forecast_table"

    object Columns {
        const val PK_ID_CITY = "pr_id_city"
        const val FORECAST_JSON = "forecast_json"
    }
}