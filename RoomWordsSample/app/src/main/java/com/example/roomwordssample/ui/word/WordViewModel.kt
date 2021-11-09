package com.example.roomwordssample.ui.word

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomwordssample.data.db.word_model.Word
import com.example.roomwordssample.data.repository.WordRepository
import kotlinx.coroutines.launch

class WordViewModel : ViewModel() {
    private val repository = WordRepository()

    private val wordListMutableLiveData = MutableLiveData<List<Word>>()
    val wordListLiveData: LiveData<List<Word>>
        get() = wordListMutableLiveData

    fun insertWord(word: Word) {
        Log.d("AppLogging", "WordViewModel / insertWord")
        viewModelScope.launch {
            repository.saveWord(word)
        }
        getAllWords()
        Log.d("AppLogging", "Word ${wordListMutableLiveData.value}")
    }

    fun getAllWords() {
        Log.d("AppLogging", "WordViewModel / getAllWords")
        viewModelScope.launch {
            val wordList = repository.getAllWords()
            wordListMutableLiveData.postValue(wordList)
        }
    }

    fun deleteAllWords() {
        Log.d("AppLogging", "WordViewModel / deleteAllWords")
        viewModelScope.launch {
            repository.deleteAllWord()
        }
        wordListMutableLiveData.postValue(emptyList())
    }
}