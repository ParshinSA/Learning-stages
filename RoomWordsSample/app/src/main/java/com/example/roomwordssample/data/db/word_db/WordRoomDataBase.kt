package com.example.roomwordssample.data.db.word_db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roomwordssample.data.db.word_model.Word

@Database(entities = [Word::class], version = WordRoomDataBase.VERSION_DB, exportSchema = false)
abstract class WordRoomDataBase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {
        const val VERSION_DB = 1
        const val NAME_DB = "word-database"
    }
}