package com.example.roomwordssample.data.db.word_db

import android.content.Context
import android.util.Log
import androidx.room.Room

object DataBase {

    lateinit var instance: WordRoomDataBase

        private set

    fun initDB(context: Context) {
        Log.d("AppLogging", "DataBase/ init DataBase")
        if (!this::instance.isInitialized) {
            Log.d("AppLogging", "DataBase / build DataBase")
            instance = Room.databaseBuilder(
                context,
                WordRoomDataBase::class.java,
                WordRoomDataBase.NAME_DB
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}