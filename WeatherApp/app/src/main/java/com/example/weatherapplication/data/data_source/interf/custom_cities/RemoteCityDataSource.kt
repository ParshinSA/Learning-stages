package com.example.weatherapplication.data.data_source.interf.custom_cities

import com.example.weatherapplication.data.networking.models.city.RemoteRequestSearchByCityNameDto
import com.example.weatherapplication.data.networking.models.city.RemoteResponseCityDto
import io.reactivex.Observable

interface RemoteCityDataSource {

    fun searchCityByName(
        remoteRequestSearchByCityNameDto: RemoteRequestSearchByCityNameDto
    ): Observable<List<RemoteResponseCityDto>>

}