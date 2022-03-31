package com.example.weatherapplication.domain.interactors

import com.example.weatherapplication.domain.interactors.interactors_interface.CityInteractor
import com.example.weatherapplication.domain.models.city.DomainCity
import com.example.weatherapplication.domain.models.city.DomainRequestSearchByCityName
import com.example.weatherapplication.domain.repository.CityRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class CityInteractorImpl @Inject constructor(
    private val repository: CityRepository
) : CityInteractor {

    override fun addCityInDatabase(domainCity: DomainCity): Completable {
        return repository.addCity(
            domainCity = domainCity
        )
    }

    override fun searchByCityName(
        domainRequestSearchByCityName: DomainRequestSearchByCityName
    ): Observable<List<DomainCity>> {
        return repository.searchByCityName(
            domainRequestSearchByCityName = domainRequestSearchByCityName
        )
    }

}