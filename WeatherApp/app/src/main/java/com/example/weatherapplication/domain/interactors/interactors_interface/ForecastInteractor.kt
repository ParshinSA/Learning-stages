package com.example.weatherapplication.domain.interactors.interactors_interface

import com.example.weatherapplication.data.database.models.forecast.Forecast
import io.reactivex.Completable
import io.reactivex.Observable

interface ForecastInteractor {

    fun oneTimeUpdateForecasts()
    fun periodUpdateForecasts()
    fun subscribeToForecastDatabase(): Observable<List<Forecast>>
    fun getLastUpdateTime():Long
    fun updateForecast(): Completable

}
