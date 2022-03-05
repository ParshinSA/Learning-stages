package com.example.weatherapplication.data.repositories.repo_implementation

import com.example.weatherapplication.data.db.forecast_db.ForecastDao
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.repositories.repo_interface.ForecastDbRepository
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ForecastDbRepositoryImpl(
    private val forecastDao: ForecastDao
) : ForecastDbRepository {

    override fun saveForecastInDatabase(forecast: Forecast) {
        forecastDao.insertForecast(forecast)
    }

    override fun observeForecastDatabase(): Flowable<List<Forecast>> {
        return forecastDao.getListForecast()
            .observeOn(Schedulers.io())
            .debounce(500, TimeUnit.MILLISECONDS)
    }

    companion object {
        const val TAG = "ForecastDBRepo_Logging"
    }
}