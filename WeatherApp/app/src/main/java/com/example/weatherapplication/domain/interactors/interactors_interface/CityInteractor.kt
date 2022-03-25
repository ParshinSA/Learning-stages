package com.example.weatherapplication.domain.interactors.interactors_interface

import com.example.weatherapplication.domain.models.city.request.DomainRequestSearchByCityNameDto
import com.example.weatherapplication.domain.models.city.response.DomainCityDto
import io.reactivex.Completable
import io.reactivex.Observable

interface CityInteractor {

    fun addCityInDatabase(domainCityDto: DomainCityDto): Completable

    fun searchByCityName(
        domainRequestSearchByCityNameDto: DomainRequestSearchByCityNameDto
    ): Observable<List<DomainCityDto>>

}
