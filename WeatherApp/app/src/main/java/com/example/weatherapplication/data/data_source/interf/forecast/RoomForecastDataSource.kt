package com.example.weatherapplication.data.data_source.interf.forecast

import com.example.weatherapplication.data.database.models.forecast.RoomForecastDto
import io.reactivex.Completable
import io.reactivex.Observable

interface RoomForecastDataSource {

    fun addForecast(roomForecastDto: RoomForecastDto): Completable
    fun getListForecastFromDatabase(): Observable<List<RoomForecastDto>>
}
