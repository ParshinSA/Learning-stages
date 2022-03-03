package com.example.weatherapplication.ui.weather.short_forecast

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.objects.AppDisposable
import com.example.weatherapplication.data.repositories.repo_interface.DatabaseRepository
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import com.example.weatherapplication.utils.SingleLiveEvent
import io.reactivex.disposables.Disposable

class ShortForecastViewModel(
    val remoteRepo: RemoteRepository,
    val appDisposable: AppDisposable,
    val databaseRepo: DatabaseRepository
) : ViewModel() {
    var disposables: Disposable? = null

    private val forecastListMutableLiveData = MutableLiveData<List<Forecast>>()
    val forecastListLiveData: LiveData<List<Forecast>>
        get() = forecastListMutableLiveData

    private val errorMessageMutableLiveData = SingleLiveEvent<String>()
    val errorMessageLiveData: LiveData<String>
        get() = errorMessageMutableLiveData

    val isLoadingMutableLiveData = MutableLiveData<Boolean>()

    init {
        disposables = databaseRepo.observeForecastDatabase().subscribe {
            isLoadingMutableLiveData.postValue(true)
            forecastListMutableLiveData.postValue(it)
            isLoadingMutableLiveData.postValue(false)
            Log.d(TAG, "DatabaseListener: $it")
        }
    }

    fun getForecastList() {
        Log.d(TAG, "getForecastList: ${disposables?.isDisposed}")
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
