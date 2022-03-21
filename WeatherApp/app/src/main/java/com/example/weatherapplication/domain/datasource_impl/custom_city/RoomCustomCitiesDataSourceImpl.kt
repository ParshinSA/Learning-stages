package com.example.weatherapplication.domain.datasource_impl.custom_city

import com.example.weatherapplication.data.data_source.custom_cities.RoomCustomCitiesDataSource
import com.example.weatherapplication.data.database.custom_cities_db.CustomCitiesDao
import com.example.weatherapplication.data.database.models.city.City
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class RoomCustomCitiesDataSourceImpl @Inject constructor(
    private val citiesDao: CustomCitiesDao
) : RoomCustomCitiesDataSource {

    override fun addCity(city: City): Completable {
        return citiesDao.addCity(city)
    }

    override fun getCustomCities(): Single<List<City>> {
        return citiesDao.getCityList()
    }

}