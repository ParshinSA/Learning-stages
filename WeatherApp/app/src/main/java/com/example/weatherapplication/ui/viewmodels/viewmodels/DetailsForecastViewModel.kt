package com.example.weatherapplication.ui.viewmodels.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.data.models.forecast.Forecast

class DetailsForecastViewModel : ViewModel() {

    private val detailsForecastMutableLiveData = MutableLiveData<Forecast>()
    val detailsForecastLiveData: LiveData<Forecast>
        get() = detailsForecastMutableLiveData

    fun setDataDetailsForecastInView(forecast: Forecast) {
        detailsForecastMutableLiveData.postValue(forecast)
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: ")
        super.onCleared()
    }

    companion object {
        const val TAG = "DetailVM_Logging"
    }

}