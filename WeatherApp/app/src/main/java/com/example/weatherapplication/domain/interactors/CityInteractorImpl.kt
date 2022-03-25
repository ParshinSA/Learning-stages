package com.example.weatherapplication.domain.interactors

import com.example.weatherapplication.domain.interactors.interactors_interface.CityInteractor
import com.example.weatherapplication.domain.models.city.request.DomainRequestSearchByCityNameDto
import com.example.weatherapplication.domain.models.city.response.DomainCityDto
import com.example.weatherapplication.domain.repository.CityRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CityInteractorImpl @Inject constructor(
    private val repository: CityRepository
) : CityInteractor {

    override fun addCityInDatabase(domainCityDto: DomainCityDto): Completable {
        return repository.addCity(
            domainCityDto = domainCityDto
        )
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }

    override fun searchByCityName(
        domainRequestSearchByCityNameDto: DomainRequestSearchByCityNameDto
    ): Observable<List<DomainCityDto>> {
        return repository.searchByCityName(
            domainRequestSearchByCityNameDto = domainRequestSearchByCityNameDto
        )
    }

}