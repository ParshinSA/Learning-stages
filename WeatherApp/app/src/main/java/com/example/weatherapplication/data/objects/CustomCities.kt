package com.example.weatherapplication.data.objects

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.repositories.CustomCitiesRepository
import io.reactivex.schedulers.Schedulers

object CustomCities {

    private val customCitiesRepository = CustomCitiesRepository()

    private val listCitiesMutableLiveData = MutableLiveData<List<City>>(emptyList())
    val listCitiesLiveData: LiveData<List<City>>
        get() = listCitiesMutableLiveData

    fun observeCustomCitiesDb() {
        Log.d(TAG, "subscribeCustomCitiesDb: start")
        AppDisposable.disposableList.add(
            customCitiesRepository.observeCustomCitiesDb().subscribe { cityList ->
                Log.d(TAG, "add livedata from bd: $cityList ")
                listCitiesMutableLiveData.postValue(cityList)
            }
        )
    }

    fun addCity(city: City) {
        AppDisposable.disposableList.add(
            customCitiesRepository.addCity(city)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d(TAG, "addCity: completed $city")
                }, {
                    Log.d(TAG, "addCity: error $it")
                })
        )
    }

    const val TAG = "CustomCities_Logging"
}