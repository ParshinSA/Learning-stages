package com.example.weatherapplication.domain.datasource_impl.custom_city

import com.example.weatherapplication.data.data_source.custom_cities.RemoteCustomCityDataSource
import com.example.weatherapplication.data.database.models.city.City
import com.example.weatherapplication.data.networking.api.SearchCityApi
import io.reactivex.Observable
import javax.inject.Inject

class RemoteCustomCityDataSourceImpl @Inject constructor(
    private val searchCityApi: SearchCityApi
) : RemoteCustomCityDataSource {

    override fun searchCityByName(cityName: String): Observable<List<City>> {
        return searchCityApi.searchCityByName(cityName = cityName)
    }
}