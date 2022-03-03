package com.example.weatherapplication.ui.weather.short_forecast

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.repositories.repo_interface.DatabaseRepository
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import com.example.weatherapplication.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

class ShortForecastViewModel(
    val remoteRepo: RemoteRepository,
    val disposable: CompositeDisposable,
    databaseRepo: DatabaseRepository
) : ViewModel() {

    private val forecastListMutableLiveData = MutableLiveData<List<Forecast>>()
    val forecastListLiveData: LiveData<List<Forecast>>
        get() = forecastListMutableLiveData

    private val errorMessageMutableLiveData = SingleLiveEvent<String>()
    val errorMessageLiveData: LiveData<String>
        get() = errorMessageMutableLiveData

    val isLoadingMutableLiveData = MutableLiveData<Boolean>()

    init {
        disposable.add(
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
        disposable.clear()
        super.onCleared()
    }

    companion object {
        const val TAG = "ShortVM_Logging"
    }
}
