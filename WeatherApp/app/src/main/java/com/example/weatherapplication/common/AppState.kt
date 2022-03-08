package com.example.weatherapplication.common

import android.util.Log

class AppState {

    var isCollapsed: Boolean = false

    fun changeStateApp(stateApp: Boolean) {
        Log.d(TAG, "appIsCollapsed: $stateApp")
        isCollapsed = stateApp
    }

    companion object {
        const val TAG = "AppState_Logging"
    }
}