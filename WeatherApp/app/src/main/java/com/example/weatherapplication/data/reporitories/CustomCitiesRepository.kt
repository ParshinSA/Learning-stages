package com.example.weatherapplication.data.reporitories

import com.example.weatherapplication.app.framework.database.models.city.City
import com.example.weatherapplication.data.data_source.custom_cities.RemoteSearchCustomCityDataSource
import com.example.weatherapplication.data.data_source.custom_cities.RoomCustomCitiesDataSource
import javax.inject.Inject

class CustomCitiesRepository @Inject constructor(
    private val remoteDataSource: RemoteSearchCustomCityDataSource,
    private val roomDataSource: RoomCustomCitiesDataSource
) {

    fun searchCityByName(cityName: String) {
        remoteDataSource.searchCityByName(cityName = cityName)
    }

    fun addCity(city: City) {
        roomDataSource.addCity(city = city)
    }

    fun getCustomCities() = roomDataSource.getCities()
}