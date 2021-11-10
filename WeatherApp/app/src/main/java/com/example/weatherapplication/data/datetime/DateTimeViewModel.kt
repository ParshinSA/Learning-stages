package com.example.weatherapplication.data.datetime

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.format
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DateTimeViewModel : ViewModel() {

    private val dateTimeMutableLiveData = MutableLiveData<String>()
    val dateTimeLiveData: LiveData<String>
        get() = dateTimeMutableLiveData

    fun listenerDateTime(tr: Boolean) {
        viewModelScope.launch {
            while (tr) {
                delay(1000)

                dateTimeMutableLiveData.postValue(DateTime.getTime().format(DateTimeFormat.DATA_TIME.format))
                Log.d(
                    "SystemLogging", "DateTimeViewModel / listenerDateTime ${
                        DateTime.getTime().format(DateTimeFormat.DATA_TIME.format)
                    }"
                )
            }
        }
    }
}
