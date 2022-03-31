package com.example.weatherapplication.domain.interactors.interactors_interface

import com.example.weatherapplication.domain.models.city.DomainCity
import com.example.weatherapplication.domain.models.city.DomainRequestSearchByCityName
import io.reactivex.Completable
import io.reactivex.Observable

interface CityInteractor {

    fun addCityInDatabase(domainCity: DomainCity): Completable

    fun searchByCityName(
        domainRequestSearchByCityName: DomainRequestSearchByCityName
    ): Observable<List<DomainCity>>

}
