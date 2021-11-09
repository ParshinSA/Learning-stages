package com.example.roomwordssample.data.db.word_db

import androidx.room.*
import com.example.roomwordssample.data.db.word_model.Word
import com.example.roomwordssample.data.db.word_model.WordContract

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: Word)

    @Query("DELETE FROM ${WordContract.TABLE_NAME}")
    suspend fun deleteAllWords()

    @Query("SELECT * FROM ${WordContract.TABLE_NAME} ORDER BY ${WordContract.Columns.WORD_TEXT} ASC")
    suspend fun getAllWords(): List<Word>
}