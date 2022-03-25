package com.example.weatherapplication.data.data_source.impl.forecast

import com.example.weatherapplication.data.data_source.interf.forecast.RoomForecastDataSource
import com.example.weatherapplication.data.database.description_db.forecast_db.ForecastDao
import com.example.weatherapplication.data.database.models.forecast.RoomForecastDto
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class RoomForecastDataSourceImpl @Inject constructor(
    private val forecastDao: ForecastDao
) : RoomForecastDataSource {

    override fun addForecast(roomForecastDto: RoomForecastDto): Completable {
        return forecastDao.insertForecast(roomForecastDto)
    }

    override fun getListForecastFromDatabase(): Observable<List<RoomForecastDto>> {
        return forecastDao.getListForecast()
    }
}