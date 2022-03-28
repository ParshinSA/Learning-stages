package com.example.weatherapplication.presentation.viewmodels.viewmodel_classes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.presentation.models.city.UiCity
import com.example.weatherapplication.presentation.models.forecast.details_forecast.UiDetailsForecast
import com.example.weatherapplication.presentation.models.forecast.details_forecast.convertToUiCityDto

class DetailsForecastViewModel : ViewModel() {

    private val detailsForecastMutableLiveData = MutableLiveData<UiDetailsForecast>()
    val detailsForecastLiveData: LiveData<UiDetailsForecast>
        get() = detailsForecastMutableLiveData

    fun setDataDetailsForecastInView(uiDetailsForecast: UiDetailsForecast) {
        detailsForecastMutableLiveData.postValue(uiDetailsForecast)
    }

    fun currentCity(): UiCity {
        return detailsForecastMutableLiveData.value!!.convertToUiCityDto()
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: ")
        super.onCleared()
    }

    companion object {
        const val TAG = "DetailVM_Logging"
    }

}