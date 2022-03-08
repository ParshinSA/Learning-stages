package com.example.weatherapplication.ui.weather.search_city

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.repositories.repo_interface.CustomCitiesDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchCityViewModel(
    private val remoteRepository: RemoteRepository,
    private val compositeDisposable: CompositeDisposable,
    private val customCitiesDbRepo: CustomCitiesDbRepository
) : ViewModel() {

    private val resultSearchListMutableLiveData = MutableLiveData<List<City>>(emptyList())
    val resultCityLiveData: LiveData<List<City>>
        get() = resultSearchListMutableLiveData


    fun addCity(city: City) {
        compositeDisposable.add(
            customCitiesDbRepo.addCity(city)
                .subscribeOn(Schedulers.io())
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
            remoteRepository.searchCity(userInput)
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