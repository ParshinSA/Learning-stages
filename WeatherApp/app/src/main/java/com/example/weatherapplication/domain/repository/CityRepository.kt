package com.example.weatherapplication.domain.repository

import com.example.weatherapplication.domain.models.city.request.DomainRequestSearchByCityNameDto
import com.example.weatherapplication.domain.models.city.response.DomainCityDto
import io.reactivex.Completable
import io.reactivex.Observable

interface CityRepository {

    fun addCity(domainCityDto: DomainCityDto): Completable

    fun searchByCityName(
        domainRequestSearchByCityNameDto: DomainRequestSearchByCityNameDto
    ): Observable<List<DomainCityDto>>

}