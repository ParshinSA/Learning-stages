package com.example.weatherapplication.domain.repository

import com.example.weatherapplication.domain.models.city.DomainCity
import com.example.weatherapplication.domain.models.city.DomainRequestSearchByCityNameDto
import io.reactivex.Completable
import io.reactivex.Observable

interface CityRepository {

    fun addCity(domainCity: DomainCity): Completable

    fun searchByCityName(
        domainRequestSearchByCityNameDto: DomainRequestSearchByCityNameDto
    ): Observable<List<DomainCity>>

}