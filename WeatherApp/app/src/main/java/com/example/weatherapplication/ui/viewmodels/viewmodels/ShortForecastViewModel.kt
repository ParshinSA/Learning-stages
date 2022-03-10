package com.example.weatherapplication.ui.viewmodels.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.ui.common.SingleLiveEvent
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.repositories.repo_interface.ForecastDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class ShortForecastViewModel(
    private val remoteRepo: RemoteRepository,
    compositeDisposable: CompositeDisposable,
    forecastDbRepo: ForecastDbRepository,
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
        compositeDisposable.add(
            forecastDbRepo.observeForecastDatabase().subscribe { forecastList ->
                isLoadingMutableLiveData.postValue(true)
                forecastListMutableLiveData.postValue(forecastList)
                isLoadingMutableLiveData.postValue(false)
                Log.d(TAG, "DatabaseListener: $forecastList")
            }
        )
    }

    fun getForecastList() {
        Log.d(TAG, "getForecastList: ")
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
