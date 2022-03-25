package com.example.weatherapplication.domain.repository

import com.example.weatherapplication.domain.models.city.response.DomainCityDto
import com.example.weatherapplication.domain.models.forecast.DomainForecastDto
import com.example.weatherapplication.domain.models.update_time.DomainLastTimeUpdate
import io.reactivex.Completable
import io.reactivex.Observable

interface ForecastRepository {

    fun requestForecast(domainCityDto: DomainCityDto): Observable<DomainForecastDto>
    fun addForecastInDatabase(domainForecastDto: DomainForecastDto): Completable
    fun getListForecastFromDatabase(): Observable<List<DomainForecastDto>>
    fun getListCity(): Observable<List<DomainCityDto>>
    fun getLastUpdateTime(): DomainLastTimeUpdate
    fun saveLastUpdateTime()

}