package com.example.weatherapplication.app.framework.datasource_impl.custom_city

import com.example.weatherapplication.app.framework.database.models.city.City
import com.example.weatherapplication.data.data_source.custom_cities.RemoteSearchCustomCityDataSource
import com.example.weatherapplication.data.networking.api.SearchCityApi
import io.reactivex.Observable

class RemoteSearchCustomCityDataSourceImpl(
    private val searchCityApi: SearchCityApi
) : RemoteSearchCustomCityDataSource {

    override fun searchCityByName(cityName: String): Observable<List<City>> {
        return searchCityApi.searchCityByName(cityName = cityName)
    }

}