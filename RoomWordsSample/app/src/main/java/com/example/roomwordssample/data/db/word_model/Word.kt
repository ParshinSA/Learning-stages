package com.example.roomwordssample.data.db.word_model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = WordContract.TABLE_NAME)
data class Word(
    @PrimaryKey()
    @ColumnInfo(name = WordContract.Columns.WORD_TEXT)
    var wordText: String
)

