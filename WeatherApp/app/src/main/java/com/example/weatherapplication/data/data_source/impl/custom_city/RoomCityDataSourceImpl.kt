package com.example.weatherapplication.data.data_source.impl.custom_city

import com.example.weatherapplication.data.data_source.interf.custom_cities.RoomCityDataSource
import com.example.weatherapplication.data.database.description_db.custom_cities_db.CustomCitiesDao
import com.example.weatherapplication.data.database.models.city.RoomCityDto
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class RoomCityDataSourceImpl @Inject constructor(
    private val cityDao: CustomCitiesDao
) : RoomCityDataSource {

    override fun addCity(roomCityDto: RoomCityDto): Completable {
        return cityDao.addCity(roomCityDto)
    }

    override fun getCity(): Observable<List<RoomCityDto>> {
        return cityDao.getCityList()
    }

}