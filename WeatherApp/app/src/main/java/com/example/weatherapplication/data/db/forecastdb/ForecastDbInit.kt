package com.example.weatherapplication.data.db.forecastdb

import android.content.Context
import androidx.room.Room

object ForecastDbInit {
    lateinit var instanceDatabaseModels: ForecastDbModels

    fun initDatabase(context: Context) {
        instanceDatabaseModels = Room.databaseBuilder(
            context,
            ForecastDbModels::class.java,
            ForecastDbContract.Database.NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}