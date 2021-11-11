package com.example.weatherapplication.data.db.models.wind

object WindContract {
    const val TABLE_NAME = "wind_table"

    object Columns {
        const val PK_ID = "pk_auto_generate_id"
        const val SPEED = "speed(m/s)"
        const val ROUTE_DEGREES = "route_degrees"
    }
}