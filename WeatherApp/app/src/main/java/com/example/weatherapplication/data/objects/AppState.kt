package com.example.weatherapplication.data.objects

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object AppState {

    private val isCollapsedAppMutableLiveData = MutableLiveData(true)
    val isCollapsedAppLiveData: LiveData<Boolean>
        get() = isCollapsedAppMutableLiveData

    fun appIsCollapsed(isCollapsed: Boolean) {
        Log.d(TAG, "appIsCollapsed: $isCollapsed")
        isCollapsedAppMutableLiveData.value = isCollapsed
    }

    const val TAG = "AppState_Logging"
}