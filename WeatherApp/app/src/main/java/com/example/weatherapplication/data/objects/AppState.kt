package com.example.weatherapplication.data.objects

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AppState {

    private val isCollapsedAppMutableLiveData = MutableLiveData(true)
    val isCollapsedAppLiveData: LiveData<Boolean>
        get() = isCollapsedAppMutableLiveData

    fun appIsCollapsed(isCollapsed: Boolean) {
        Log.d(TAG, "appIsCollapsed: $isCollapsed")
        isCollapsedAppMutableLiveData.value = isCollapsed
    }

    companion object {
        const val TAG = "AppState_Logging"
    }
}