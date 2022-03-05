package com.example.weatherapplication.data.repositories.repo_interface

import com.example.weatherapplication.data.models.city.City
import io.reactivex.Completable
import io.reactivex.Observable

interface CustomCitiesDbRepository {
    fun observeCustomCitiesDb(): Observable<List<City>>
    fun addCity(city: City): Completable
}
