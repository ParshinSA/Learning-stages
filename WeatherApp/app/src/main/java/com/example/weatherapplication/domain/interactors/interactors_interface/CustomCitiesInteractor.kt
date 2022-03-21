package com.example.weatherapplication.domain.interactors.interactors_interface

import com.example.weatherapplication.data.database.models.city.City
import io.reactivex.Completable
import io.reactivex.Observable

interface CustomCitiesInteractor {

    fun addCityInDatabase(city: City): Completable
    fun searchForCustomCityByName(cityName: String): Observable<List<City>>

}
