package com.example.weatherapplication.domain.datasource_impl.forecast

import com.example.weatherapplication.data.data_source.forecast.RoomForecastDataSource
import com.example.weatherapplication.data.database.forecast_db.ForecastDao
import com.example.weatherapplication.data.database.models.forecast.Forecast
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RoomForecastDataSourceImpl @Inject constructor(
    private val forecastDao: ForecastDao
) : RoomForecastDataSource {

    override fun addForecast(forecast: Forecast): Completable {
        return forecastDao.insertForecast(forecast)
    }

    override fun subscribeToForecastDatabase(): Observable<List<Forecast>> {
        return forecastDao.getListForecast()
            .observeOn(Schedulers.io())
            .debounce(500, TimeUnit.MILLISECONDS)
    }
}