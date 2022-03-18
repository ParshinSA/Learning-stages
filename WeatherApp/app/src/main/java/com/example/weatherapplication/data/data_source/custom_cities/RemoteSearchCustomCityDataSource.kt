package com.example.weatherapplication.data.data_source.custom_cities

import com.example.weatherapplication.app.framework.database.models.city.City
import io.reactivex.Observable

interface RemoteSearchCustomCityDataSource {

    fun searchCityByName(cityName: String): Observable<List<City>>

}