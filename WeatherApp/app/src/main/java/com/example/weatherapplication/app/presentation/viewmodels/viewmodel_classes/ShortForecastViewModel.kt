package com.example.weatherapplication.app.presentation.viewmodels.viewmodel_classes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.app.framework.database.models.city.City
import com.example.weatherapplication.app.framework.database.models.forecast.Forecast
import com.example.weatherapplication.data.data_source.custom_cities.RoomCustomCitiesDataSource
import com.example.weatherapplication.data.data_source.forecast.RoomForecastDataSource
import com.example.weatherapplication.data.data_source.forecast.RemoteForecastDataSource
import com.example.weatherapplication.app.common.SingleLiveEvent
import com.example.weatherapplication.app.presentation.viewmodels.BaseViewModel

class ShortForecastViewModel(
    private val remoteRepo: RemoteForecastDataSource,
    private val roomForecastDbRepo: RoomForecastDataSource,
    private val roomCustomCitiesDbRepo: RoomCustomCitiesDataSource
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
            roomCustomCitiesDbRepo.getCities()
                .subscribe({ cityListFromDb ->
                    customCityListMutableLiveData.postValue(cityListFromDb)
                }, {
                    Log.d(TAG, "observeCustomCityDb: $it")
                })
        )
    }

    private fun observeForecastDb() {
        compositeDisposable.add(
            roomForecastDbRepo.getForecasts()
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
