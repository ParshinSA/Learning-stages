package com.example.weatherapplication.interactors.custom_cities

import com.example.weatherapplication.app.framework.database.models.city.City
import com.example.weatherapplication.data.reporitories.CustomCitiesRepository

class AddCityToCustomCityList(
    private val repository: CustomCitiesRepository
) {

    fun execute(city: City) = repository.addCity(city = city)

}