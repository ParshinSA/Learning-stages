package com.example.weatherapplication.data.repositories

import com.example.weatherapplication.data.db.custom_cities_db.CustomCitiesDbInit
import com.example.weatherapplication.data.models.city.City
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class CustomCitiesRepository {

    private val citiesDao = CustomCitiesDbInit.instanceCustomCitiesModels.getCustomCitiesDao()

    fun observeCustomCitiesDb(): Observable<List<City>> {
        return citiesDao.getCityList()
            .observeOn(Schedulers.io())
    }

    fun addCity(city: City): Completable {
        return Completable.create { subscriber ->
            citiesDao.addCity(city)
            subscriber.onComplete()
        }
    }
}