package com.example.weatherapplication.presentation.common

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Parshin Sergey
 * Ксласс описывающий в фокусе или нет (свернуто ли нет) приложение
 * @see isCollapsed текущее состояние приложение
 * @see changeStateApp метод изменяющий состояние приложения
 * */

@Singleton
class AppState @Inject constructor() {

    var isCollapsed: Boolean = false

    fun changeStateApp(stateApp: Boolean) {
        Log.d(TAG, "appIsCollapsed: $stateApp")
        isCollapsed = stateApp
    }

    companion object {
        const val TAG = "AppState_Logging"
    }
}