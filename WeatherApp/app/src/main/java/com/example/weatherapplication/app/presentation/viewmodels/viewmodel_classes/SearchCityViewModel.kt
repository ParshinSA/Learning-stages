package com.example.weatherapplication.app.presentation.viewmodels.viewmodel_classes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.app.framework.database.models.city.City
import com.example.weatherapplication.data.data_source.custom_cities.RoomCustomCitiesDataSource
import com.example.weatherapplication.data.data_source.forecast.RemoteForecastDataSource
import com.example.weatherapplication.app.presentation.viewmodels.BaseViewModel
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class SearchCityViewModel(
    private val remoteDataSource: RemoteForecastDataSource,
    private val roomCustomCitiesDataSource: RoomCustomCitiesDataSource
) : BaseViewModel() {

    private val resultSearchListMutableLiveData = MutableLiveData<List<City>>(emptyList())
    val resultCityLiveData: LiveData<List<City>>
        get() = resultSearchListMutableLiveData

    fun addCity(city: City) {
        compositeDisposable.add(
            roomCustomCitiesDataSource.addCity(city)
                .subscribe({
                    Log.d(TAG, "addCity: completed $city")
                }, {
                    Log.d(TAG, "addCity: error $it")
                })
        )
    }

    fun textInputProcessing(userInput: Observable<String>) {
        compositeDisposable.add(
            userInput
                .filter { it.length >= 3 }
                .debounce(350, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribe {
                    searchCity(it)
                }
        )
    }

    private fun searchCity(userInput: String) {
        compositeDisposable.add(
            remoteDataSource.searchCity(userInput)
                .subscribe({ listCity ->
                    Log.d(TAG, "searchCity: RESULT $listCity")
                    resultSearchListMutableLiveData.postValue(listCity)
                }, {
                    Log.d(TAG, "searchCity: ERROR $it")
                })
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    companion object {
        const val TAG = "SearchCityVM_Logging"
    }
}