package com.example.weatherapplication.ui.weather.shortforecastlist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.repositories.DatabaseRepository
import com.example.weatherapplication.data.repositories.RemoteRepository
import kotlinx.coroutines.launch

class ShortForecastListViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val remoteRepo = RemoteRepository(application)
    private val databaseRepo = DatabaseRepository()

    private val forecastListMutableLiveData = MutableLiveData<List<Forecast>>()
    val forecastListLiveData: LiveData<List<Forecast>>
        get() = forecastListMutableLiveData

    private val errorMessageMutableLiveData = MutableLiveData<String>()
    val errorMessageLiveData: LiveData<String>
        get() = errorMessageMutableLiveData

    private val isLoadingMutableLiveData = MutableLiveData<Boolean>()
    val isLoadingLiveData: LiveData<Boolean>
        get() = isLoadingMutableLiveData

    fun getForecastList(isForcedUpdate: Boolean = false) {
        isLoadingMutableLiveData.postValue(true)
        viewModelScope.launch {

            val listForecast = if (!isForcedUpdate) {
                databaseRepo.getForecastFromDatabase()
            } else emptyList()

            if (listForecast.isEmpty()) {
                remoteRepo.oneTimeUpdateForecastAllCity()
            }

            forecastListMutableLiveData.postValue(listForecast)
            isLoadingMutableLiveData.postValue(false)
        }
    }

    fun errorMessage(message: String) {
        errorMessageMutableLiveData.postValue(message)
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared")
        super.onCleared()
    }

    companion object {
        const val TAG = "ShortVM_Logging"
    }
}
