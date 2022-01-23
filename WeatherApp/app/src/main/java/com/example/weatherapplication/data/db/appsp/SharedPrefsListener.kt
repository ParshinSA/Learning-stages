package com.example.weatherapplication.data.db.appsp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object SharedPrefsListener {

    private val listenerSharedPrefsMutableLiveData = MutableLiveData(false)
    val listenerSharedPrefs: LiveData<Boolean>
        get() = listenerSharedPrefsMutableLiveData

    fun isSharedPrefs(isChanged: Boolean) {
        listenerSharedPrefsMutableLiveData.postValue(isChanged)
    }
}