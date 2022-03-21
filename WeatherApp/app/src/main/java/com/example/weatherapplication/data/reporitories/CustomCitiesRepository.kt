package com.example.weatherapplication.data.reporitories

import com.example.weatherapplication.data.data_source.custom_cities.RemoteCustomCityDataSource
import com.example.weatherapplication.data.data_source.custom_cities.RoomCustomCitiesDataSource
import com.example.weatherapplication.data.database.models.city.City
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class CustomCitiesRepository @Inject constructor(
    private val remoteDataSource: RemoteCustomCityDataSource,
    private val roomDataSource: RoomCustomCitiesDataSource
) {

    fun addCity(city: City): Completable {
       return roomDataSource.addCity(city = city)
    }

    fun searchCityByName(cityName: String): Observable<List<City>> {
        return remoteDataSource.searchCityByName(cityName = cityName)
    }

}