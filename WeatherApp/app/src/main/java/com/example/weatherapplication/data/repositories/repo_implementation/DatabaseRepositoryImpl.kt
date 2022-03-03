package com.example.weatherapplication.data.repositories.repo_implementation

import com.example.weatherapplication.data.db.forecast_db.ForecastDao
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.repositories.repo_interface.DatabaseRepository
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class DatabaseRepositoryImpl(
    private val forecastDao: ForecastDao
) : DatabaseRepository {

    override fun saveForecastInDatabase(forecast: Forecast) {
        forecastDao.insertListForecast(forecast)
    }

    override fun observeForecastDatabase(): Flowable<List<Forecast>> {
        return forecastDao.getWeatherForecast()
            .observeOn(Schedulers.io())
            .debounce(500, TimeUnit.MILLISECONDS)
    }

    companion object {
        const val TAG = "ForecastDBRepo_Logging"
    }
}