package com.example.weatherapplication.ui.weather.detailsforecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.data.models.forecast.Forecast
import timber.log.Timber

class DetailsForecastViewModel : ViewModel() {

    private val detailsForecastMutableLiveData = MutableLiveData<Forecast>()
    val detailsForecastLiveData: LiveData<Forecast>
        get() = detailsForecastMutableLiveData

    fun setDataDetailsForecastInView(forecast: Forecast) {
        detailsForecastMutableLiveData.postValue(forecast)
    }

    override fun onCleared() {
        Timber.d("onCleared")
        super.onCleared()
    }
}