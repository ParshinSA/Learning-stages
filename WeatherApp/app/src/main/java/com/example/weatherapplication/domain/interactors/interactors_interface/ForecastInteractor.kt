package com.example.weatherapplication.domain.interactors.interactors_interface

import com.example.weatherapplication.domain.models.forecast.DomainForecast
import com.example.weatherapplication.domain.models.update_time.DomainLastTimeUpdate
import io.reactivex.Completable
import io.reactivex.Observable

interface ForecastInteractor {

    fun updateForecast()
    fun getListForecastFromDatabase(): Observable<List<DomainForecast>>
    fun getLastUpdateTime(): DomainLastTimeUpdate

    fun updateForecastFromService(): Completable

}
