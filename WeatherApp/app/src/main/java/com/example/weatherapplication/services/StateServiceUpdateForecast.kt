package com.example.weatherapplication.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object StateServiceUpdateForecast {

    private val mutableStateUpdate = MutableLiveData<Boolean>()
    val stateUpdate: LiveData<Boolean>
        get() = mutableStateUpdate

    fun changeStateUpdate(isUpdate: Boolean) {
        mutableStateUpdate.postValue(isUpdate)
    }
}