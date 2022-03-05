package com.example.weatherapplication.data.objects

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.repositories.repo_interface.CustomCitiesDbRepository
import io.reactivex.schedulers.Schedulers

class CustomCities(
    private val customCitiesDbRepository: CustomCitiesDbRepository,
    private val appDisposable: AppDisposable
) {

    private val listCitiesMutableLiveData = MutableLiveData<List<City>>()
    val listCitiesLiveData: LiveData<List<City>>
        get() = listCitiesMutableLiveData

    init {
        Log.d(TAG, "subscribeCustomCitiesDb: start")
        appDisposable.disposableList.add(
            customCitiesDbRepository.observeCustomCitiesDb().subscribe { cityList ->
                Log.d(TAG, "add livedata from bd: $cityList ")
                listCitiesMutableLiveData.postValue(cityList)
            }
        )
    }

    fun addCity(city: City) {
        appDisposable.disposableList.add(
            customCitiesDbRepository.addCity(city)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d(TAG, "addCity: completed $city")
                }, {
                    Log.d(TAG, "addCity: error $it")
                })
        )
    }

    companion object {
        const val TAG = "CustomCities_Logging"
    }
}