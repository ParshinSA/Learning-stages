package com.example.weatherapplication.data.data_source.custom_cities

import com.example.weatherapplication.data.database.models.city.City
import io.reactivex.Observable

interface RemoteCustomCityDataSource {

    fun searchCityByName(cityName: String): Observable<List<City>>

}