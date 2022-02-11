package com.example.weatherapplication.data.db.customcitiesdb

import android.content.Context
import androidx.room.Room

object CustomCitiesDbInit {
    lateinit var instanceCustomCitiesModels: CustomCitiesDbModels

    fun initDatabase(context: Context) {
        instanceCustomCitiesModels = Room.databaseBuilder(
            context,
            CustomCitiesDbModels::class.java,
            CustomCitiesContract.Database.NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}