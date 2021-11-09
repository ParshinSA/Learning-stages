package com.example.roomwordssample.data.repository

import android.util.Log
import com.example.roomwordssample.data.db.word_db.DataBase
import com.example.roomwordssample.data.db.word_model.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordRepository {

    private val wordDao = DataBase.instance.wordDao()

    suspend fun getAllWords(): List<Word> {
        Log.d("AppLogging", "WordRepository / getAllWords")
        return withContext(Dispatchers.IO) {
            wordDao.getAllWords()
        }
    }

    suspend fun saveWord(word: Word) {
        Log.d("AppLogging", "WordRepository / saveWord")
        withContext(Dispatchers.IO) {
            wordDao.insertWord(word)
        }
    }

    suspend fun deleteAllWord() {
        Log.d("AppLogging", "WordRepository / deleteAllWord")
        withContext(Dispatchers.IO) {
            wordDao.deleteAllWords()
        }
    }
}