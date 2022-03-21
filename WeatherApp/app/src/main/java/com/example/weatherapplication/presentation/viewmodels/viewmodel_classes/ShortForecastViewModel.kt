package com.example.weatherapplication.presentation.viewmodels.viewmodel_classes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.data.database.models.city.City
import com.example.weatherapplication.data.database.models.forecast.Forecast
import com.example.weatherapplication.domain.interactors.interactors_interface.ForecastInteractor
import com.example.weatherapplication.presentation.common.SingleLiveEvent
import com.example.weatherapplication.presentation.viewmodels.BaseViewModel

class ShortForecastViewModel(
    private val interactor: ForecastInteractor
) : BaseViewModel() {

    private val forecastListMutableLiveData = MutableLiveData<List<Forecast>>()
    val forecastListLiveData: LiveData<List<Forecast>>
        get() = forecastListMutableLiveData

    private val errorMessageMutableLiveData = SingleLiveEvent<String>()
    val errorMessageLiveData: LiveData<String>
        get() = errorMessageMutableLiveData

    private val customCityListMutableLiveData = MutableLiveData<List<City>>(emptyList())
    val customCityListLivaData: LiveData<List<City>>
        get() = customCityListMutableLiveData

    val isLoadingMutableLiveData = MutableLiveData<Boolean>()

    init {
        compositeDisposable.add(
            interactor.subscribeToForecastDatabase()
                .subscribe({ forecastList ->
                    forecastListMutableLiveData.postValue(forecastList)
                },
                    {
                        Log.d(TAG, "update forecastListMutableLiveData: error $it")
                    }
                )
        )
    }


    fun getForecastList() {
        Log.d(TAG, "getForecastList: ")
        isLoadingMutableLiveData.postValue(true)
        interactor.oneTimeUpdateForecasts()
        isLoadingMutableLiveData.postValue(false)
    }

    fun getLastUpdateTime(): Long {
        return interactor.getLastUpdateTime()
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
