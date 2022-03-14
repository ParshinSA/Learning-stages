package com.example.weatherapplication.ui.viewmodels.viewmodel_classes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.repositories.repo_interface.CustomCitiesDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.ForecastDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import com.example.weatherapplication.ui.common.SingleLiveEvent
import com.example.weatherapplication.ui.viewmodels.BaseViewModel

class ShortForecastViewModel(
    private val remoteRepo: RemoteRepository,
    private val forecastDbRepo: ForecastDbRepository,
    private val customCitiesDbRepo: CustomCitiesDbRepository
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
        observeForecastDb()
        observeCustomCityDb()
    }

    private fun observeCustomCityDb() {
        compositeDisposable.add(
            customCitiesDbRepo.getCityList()
                .subscribe({ cityListFromDb ->
                    customCityListMutableLiveData.postValue(cityListFromDb)
                }, {
                    Log.d(TAG, "observeCustomCityDb: $it")
                })
        )
    }

    private fun observeForecastDb() {
        compositeDisposable.add(
            forecastDbRepo.observeForecastDatabase()
                .subscribe { forecastList ->
                    forecastListMutableLiveData.postValue(forecastList)
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
