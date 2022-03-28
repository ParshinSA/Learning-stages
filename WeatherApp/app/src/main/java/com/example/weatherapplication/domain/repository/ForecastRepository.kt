package com.example.weatherapplication.domain.repository

import com.example.weatherapplication.domain.models.city.DomainCity
import com.example.weatherapplication.domain.models.forecast.DomainForecast
import com.example.weatherapplication.domain.models.update_time.DomainLastTimeUpdate
import io.reactivex.Completable
import io.reactivex.Observable

interface ForecastRepository {

    fun requestForecast(domainCity: DomainCity): Observable<DomainForecast>
    fun addForecastInDatabase(domainForecast: DomainForecast): Completable
    fun getListForecastFromDatabase(): Observable<List<DomainForecast>>
    fun getListCity(): Observable<List<DomainCity>>
    fun getLastUpdateTime(): DomainLastTimeUpdate
    fun saveLastUpdateTime()

}