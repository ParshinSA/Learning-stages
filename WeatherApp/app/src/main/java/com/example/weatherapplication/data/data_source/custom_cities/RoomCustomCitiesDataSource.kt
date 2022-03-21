package com.example.weatherapplication.data.data_source.custom_cities

import com.example.weatherapplication.data.database.models.city.City
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface RoomCustomCitiesDataSource {

    fun addCity(city: City): Completable
    fun getCustomCities(): Single<List<City>>

}