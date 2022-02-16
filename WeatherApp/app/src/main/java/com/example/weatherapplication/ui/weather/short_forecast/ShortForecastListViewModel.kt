package com.example.weatherapplication.ui.weather.short_forecast

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.objects.AppDisposable
import com.example.weatherapplication.data.repositories.ForecastDbRepository
import com.example.weatherapplication.data.repositories.RemoteRepository
import com.example.weatherapplication.utils.SingleLiveEvent

class ShortForecastListViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val remoteRepo = RemoteRepository(application)
    private val databaseRepo = ForecastDbRepository()

    private val forecastListMutableLiveData = MutableLiveData<List<Forecast>>()
    val forecastListLiveData: LiveData<List<Forecast>>
        get() = forecastListMutableLiveData

    private val errorMessageMutableLiveData = SingleLiveEvent<String>()
    val errorMessageLiveData: LiveData<String>
        get() = errorMessageMutableLiveData

    val isLoadingMutableLiveData = MutableLiveData<Boolean>()


    init {
        AppDisposable.disposableList.add(
            databaseRepo.observeForecastDatabase().subscribe {
                isLoadingMutableLiveData.postValue(true)
                forecastListMutableLiveData.postValue(it)
                isLoadingMutableLiveData.postValue(false)
                Log.d(TAG, "DatabaseListener: $it")
            })
    }

    fun getForecastList() {
        isLoadingMutableLiveData.postValue(true)
        remoteRepo.oneTimeUpdateForecastAllCity()
        isLoadingMutableLiveData.postValue(false)
    }

    fun errorMessage(message: String) {
        errorMessageMutableLiveData.postValue(message)
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: VM cleared")
        super.onCleared()
    }

    companion object {
        const val TAG = "ShortVM_Logging"
    }
}
