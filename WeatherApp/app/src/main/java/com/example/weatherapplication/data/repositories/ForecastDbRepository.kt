package com.example.weatherapplication.data.repositories

import android.util.Log
import com.example.weatherapplication.data.db.forecastdb.ForecastDbInit
import com.example.weatherapplication.data.models.forecast.Forecast
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ForecastDbRepository {

    private val forecastDao = ForecastDbInit.instanceDatabaseModels.getForecastDao()

    fun saveForecastInDatabase(forecast: Forecast) =
        forecastDao.insertListForecast(forecast)

    fun observeForecastDatabase(): Flowable<List<Forecast>> {
        return forecastDao.getWeatherForecast()
            .doOnNext { Log.d(TAG, "observeForecastDatabase: from db $it")}
            .observeOn(Schedulers.io())
            .debounce(500, TimeUnit.MILLISECONDS)
    }

    companion object {
        const val TAG = "ForecastDBRepo_Logging"
    }
}