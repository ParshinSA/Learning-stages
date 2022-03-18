package com.example.weatherapplication.app.framework.datasource_impl.forecast

import com.example.weatherapplication.app.framework.database.forecast_db.ForecastDao
import com.example.weatherapplication.app.framework.database.models.forecast.Forecast
import com.example.weatherapplication.data.data_source.forecast.RoomForecastDataSource
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class RoomForecastDataSourceImpl(
    private val forecastDao: ForecastDao
) : RoomForecastDataSource {

    override fun addForecast(forecast: Forecast) {
        return forecastDao.insertForecast(forecast)
    }

    override fun getForecasts(): Observable<List<Forecast>> {
        return forecastDao.getListForecast()
            .observeOn(Schedulers.io())
            .debounce(500, TimeUnit.MILLISECONDS)
    }
}