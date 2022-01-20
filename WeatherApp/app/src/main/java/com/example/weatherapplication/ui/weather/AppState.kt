package com.example.weatherapplication.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object AppState {

    private val isCollapsedAppMutableLiveData = MutableLiveData(true)
    val isCollapsedAppLiveData: LiveData<Boolean>
        get() = isCollapsedAppMutableLiveData

    fun changeStateCollapsedApp(isCollapsed: Boolean) {
        isCollapsedAppMutableLiveData.value = isCollapsed
    }
}