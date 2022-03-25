package com.example.weatherapplication.presentation.viewmodels.viewmodel_classes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.presentation.models.city.UiCityDto
import com.example.weatherapplication.presentation.models.forecast.details.UiDetailsForecastDto
import com.example.weatherapplication.presentation.models.forecast.details.convertToUiCityDto

class DetailsForecastViewModel : ViewModel() {

    private val detailsForecastMutableLiveData = MutableLiveData<UiDetailsForecastDto>()
    val detailsForecastLiveData: LiveData<UiDetailsForecastDto>
        get() = detailsForecastMutableLiveData

    fun setDataDetailsForecastInView(responseForecast: UiDetailsForecastDto) {
        detailsForecastMutableLiveData.postValue(responseForecast)
    }

    fun currentCity(): UiCityDto {
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