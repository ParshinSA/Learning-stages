package com.example.weatherapplication.data.data_source.custom_cities

import com.example.weatherapplication.app.framework.database.models.city.City
import io.reactivex.Completable
import io.reactivex.Observable

interface RoomCustomCitiesDataSource {

    fun addCity(city: City)
    fun getCities(): Observable<List<City>>

}
