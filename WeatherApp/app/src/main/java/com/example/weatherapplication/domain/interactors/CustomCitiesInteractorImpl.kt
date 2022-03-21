package com.example.weatherapplication.domain.interactors

import com.example.weatherapplication.data.database.models.city.City
import com.example.weatherapplication.data.reporitories.CustomCitiesRepository
import com.example.weatherapplication.domain.interactors.interactors_interface.CustomCitiesInteractor
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CustomCitiesInteractorImpl @Inject constructor(
    private val repository: CustomCitiesRepository
) : CustomCitiesInteractor {

    override fun addCityInDatabase(city: City): Completable {
        return repository.addCity(city = city)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }

    override fun searchForCustomCityByName(cityName: String): Observable<List<City>> {
        return repository.searchCityByName(cityName = cityName)
    }

}