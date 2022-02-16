package com.example.weatherapplication.ui.weather.search_city

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.objects.AppDisposable
import com.example.weatherapplication.data.repositories.RemoteRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class SearchCityViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val remoteRepository = RemoteRepository(application)
    private val disposeBack = CompositeDisposable()

    private val resultSearchListMutableLiveData = MutableLiveData<List<City>>(emptyList())
    val resultCityLiveData: LiveData<List<City>>
        get() = resultSearchListMutableLiveData

    fun textInputProcessing(userInput: Observable<String>) {
        disposeBack.add(
            userInput
                .filter { it.length >= 3 }
                .debounce(250, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribe {
                    searchCity(it)
                }
        )
    }

    private fun searchCity(userInput: String) {
        disposeBack.add(
            remoteRepository.searchCity(userInput)
                .subscribe({ listCity->
                    Log.d(TAG, "searchCity: RESULT $listCity")
                    resultSearchListMutableLiveData.postValue(listCity)
                }, {
                    Log.d(TAG, "searchCity: ERROR $it")
                })
        )
    }

    override fun onCleared() {
        disposeBack.clear()
        super.onCleared()
    }

    companion object {
        const val TAG = "SearchCityVM_Logging"
    }
}