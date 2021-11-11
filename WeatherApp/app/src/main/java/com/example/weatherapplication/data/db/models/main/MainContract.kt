package com.example.weatherapplication.data.db.models.main

object MainContract {
    const val TABLE_NAME = "main_table"

    object Columns {
        const val PK_ID = "pk_auto_generate_id"
        const val TEMP = "temp(C)"
        const val PRESSURE = "pressure(hPa)"
        const val HUMIDITY = "humidity(%)"
    }
}