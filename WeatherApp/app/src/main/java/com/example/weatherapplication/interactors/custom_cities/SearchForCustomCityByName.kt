package com.example.weatherapplication.interactors.custom_cities

import com.example.weatherapplication.data.reporitories.CustomCitiesRepository

class SearchForCustomCityByName(
    private val repository: CustomCitiesRepository
) {

    fun execute(cityName: String) {
        repository.searchCityByName(cityName = cityName)
    }

}