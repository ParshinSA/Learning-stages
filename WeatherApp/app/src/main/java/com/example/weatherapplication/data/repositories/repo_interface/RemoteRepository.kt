package com.example.weatherapplication.data.repositories.repo_interface

import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.models.report.DataHistory
import com.example.weatherapplication.ui.weather.report.Period
import io.reactivex.Observable
import io.reactivex.Single

interface RemoteRepository {

    fun oneTimeUpdateForecastAllCity()
    fun periodUpdateForecastAllCityList()

    fun requestForecastAllCity(cityList: List<City>): Observable<Forecast>
    fun searchCity(userInput: String): Single<List<City>>
    fun requestHistory(forecast: Forecast, period: Period): Observable<DataHistory>
}