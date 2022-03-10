package com.example.weatherapplication.common

import android.util.Log

/**
 * @author Parshin Sergey
 * Ксласс описывающий в фокусе или нет (свернуто ли нет) приложение
 * @see isCollapsed текущее состояние приложение
 * @see changeStateApp метод изменяющий состояние приложения
 * */

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