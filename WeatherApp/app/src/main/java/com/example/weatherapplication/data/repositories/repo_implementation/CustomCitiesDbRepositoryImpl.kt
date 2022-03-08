package com.example.weatherapplication.data.repositories.repo_implementation

import com.example.weatherapplication.data.db.custom_cities_db.CustomCitiesDao
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.repositories.repo_interface.CustomCitiesDbRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class CustomCitiesDbRepositoryImpl(
    private val citiesDao: CustomCitiesDao
) : CustomCitiesDbRepository {

   override fun getCityList(): Single<List<City>> {
        return citiesDao.getCityList()
            .observeOn(Schedulers.io())
    }

    override fun addCity(city: City): Completable {
        return Completable.create { subscriber ->
            citiesDao.addCity(city)
            subscriber.onComplete()
        }
    }
}