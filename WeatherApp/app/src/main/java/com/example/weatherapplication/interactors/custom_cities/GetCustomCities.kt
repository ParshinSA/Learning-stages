package com.example.weatherapplication.interactors.custom_cities

import com.example.weatherapplication.data.reporitories.CustomCitiesRepository

class GetCustomCities(
    private val repository: CustomCitiesRepository
) {

    fun execute() = repository.getCustomCities()

}