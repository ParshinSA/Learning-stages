package com.example.weatherapplication.ui.weather.searchcity

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.objects.AppDisposable
import com.example.weatherapplication.data.repositories.RemoteRepository

class SearchCityViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val remoteRepository = RemoteRepository(application)

    private val resultSearchListMutableLiveData = MutableLiveData<List<City>>(emptyList())
    val resultCityLiveData: LiveData<List<City>>
        get() = resultSearchListMutableLiveData


    fun searchCity(userInput: String) {
        AppDisposable.disposableList.add(
            remoteRepository.searchCity(userInput)
                .subscribe({
                    Log.d(TAG, "searchCity: result $it")
                    resultSearchListMutableLiveData.postValue(it)
                }, {
                    Log.d(TAG, "searchCity: ERROR $it")
                })
        )
    }

    companion object {
        const val TAG = "SearchCityVM_Logging"
    }
}