package com.example.weatherapplication.data.data_source.impl.custom_city

import com.example.weatherapplication.data.data_source.interf.custom_cities.RemoteCityDataSource
import com.example.weatherapplication.data.networking.api.SearchCityApi
import com.example.weatherapplication.data.networking.models.city.request.RemoteRequestSearchByCityNameDto
import com.example.weatherapplication.data.networking.models.city.response.RemoteResponseCityDto
import io.reactivex.Observable
import javax.inject.Inject

class RemoteCityDataSourceImpl @Inject constructor(
    private val retrofitSearchCityApi: SearchCityApi
) : RemoteCityDataSource {

    override fun searchCityByName(
        remoteRequestSearchByCityNameDto: RemoteRequestSearchByCityNameDto
    ): Observable<List<RemoteResponseCityDto>> {
        return retrofitSearchCityApi
            .searchCityByName(
                name = remoteRequestSearchByCityNameDto.name
            )
    }
}