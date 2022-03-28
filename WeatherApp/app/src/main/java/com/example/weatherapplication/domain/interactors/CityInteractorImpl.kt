package com.example.weatherapplication.domain.interactors

import com.example.weatherapplication.domain.interactors.interactors_interface.CityInteractor
import com.example.weatherapplication.domain.models.city.DomainCity
import com.example.weatherapplication.domain.models.city.DomainRequestSearchByCityNameDto
import com.example.weatherapplication.domain.repository.CityRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CityInteractorImpl @Inject constructor(
    private val repository: CityRepository
) : CityInteractor {

    override fun addCityInDatabase(domainCity: DomainCity): Completable {
        return repository.addCity(
            domainCity = domainCity
        )
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }

    override fun searchByCityName(
        domainRequestSearchByCityNameDto: DomainRequestSearchByCityNameDto
    ): Observable<List<DomainCity>> {
        return repository.searchByCityName(
            domainRequestSearchByCityNameDto = domainRequestSearchByCityNameDto
        )
    }

}