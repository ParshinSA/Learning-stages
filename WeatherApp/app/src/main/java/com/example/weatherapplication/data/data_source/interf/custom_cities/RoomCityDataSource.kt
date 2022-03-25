package com.example.weatherapplication.data.data_source.interf.custom_cities

import com.example.weatherapplication.data.database.models.city.RoomCityDto
import io.reactivex.Completable
import io.reactivex.Observable

interface RoomCityDataSource {

    fun addCity(roomCityDto: RoomCityDto): Completable
    fun getCity(): Observable<List<RoomCityDto>>

}