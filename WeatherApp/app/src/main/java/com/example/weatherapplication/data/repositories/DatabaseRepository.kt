package com.example.weatherapplication.data.repositories

import android.util.Log
import com.example.weatherapplication.data.db.appdb.AppDatabaseInit
import com.example.weatherapplication.data.models.forecast.Forecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseRepository {

    private val forecastDao = AppDatabaseInit.instanceDatabaseModels.getForecastDao()

    suspend fun saveForecastInDatabase(forecast: Forecast) =
        withContext(Dispatchers.IO) {
            forecastDao.insertListForecast(forecast)
        }


    suspend fun getForecastFromDatabase(): List<Forecast> {
        return withContext(Dispatchers.IO) {
            try {
                val listForecastDB = forecastDao.getWeatherForecast()
                Log.d(TAG, "getForecastFromDatabase: $listForecastDB")
                listForecastDB
            } catch (t: Throwable) {
                Log.d(TAG, "getForecastFromDatabase: ERROR t:$t")
                emptyList()
            }
        }
    }

    companion object {
        const val TAG = "DBRepo_Logging"
    }
}