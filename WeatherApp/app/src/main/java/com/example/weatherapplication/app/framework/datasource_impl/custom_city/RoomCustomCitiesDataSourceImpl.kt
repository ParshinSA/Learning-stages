package com.example.weatherapplication.app.framework.datasource_impl.custom_city

import com.example.weatherapplication.app.framework.database.custom_cities_db.CustomCitiesDao
import com.example.weatherapplication.app.framework.database.models.city.City
import com.example.weatherapplication.data.data_source.custom_cities.RoomCustomCitiesDataSource
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class RoomCustomCitiesDataSourceImpl(
    private val citiesDao: CustomCitiesDao
) : RoomCustomCitiesDataSource {

    override fun addCity(city: City) {
       return citiesDao.addCity(city)
    }

    override fun getCities(): Observable<List<City>> {
        return citiesDao.getCityList()
            .subscribeOn(Schedulers.io())
    }
}