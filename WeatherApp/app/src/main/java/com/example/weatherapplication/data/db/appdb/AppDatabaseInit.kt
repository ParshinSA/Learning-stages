package com.example.weatherapplication.data.db.appdb

import android.content.Context
import androidx.room.Room

object AppDatabaseInit {
    lateinit var instanceDatabaseModels: DatabaseModels

    fun initDatabase(context: Context) {
        instanceDatabaseModels = Room.databaseBuilder(
            context,
            DatabaseModels::class.java,
            AppDatabaseContract.Database.NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}