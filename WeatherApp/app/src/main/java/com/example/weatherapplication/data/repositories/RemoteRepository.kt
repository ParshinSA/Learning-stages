package com.example.weatherapplication.data.repositories

import android.content.Context
import android.util.Log
import androidx.work.*
import com.example.weatherapplication.DefaultListCity
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.models.report.HistoryData
import com.example.weatherapplication.data.retrofit.Networking
import com.example.weatherapplication.workers.UpdateForecastWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.concurrent.TimeUnit


class RemoteRepository(
    private val context: Context
) {
    private val databaseForecastRepository = DatabaseRepository()

    fun periodUpdateForecastAllCityList() {
        val updateWorker =
            PeriodicWorkRequestBuilder<UpdateForecastWorker>(15, TimeUnit.MINUTES)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                UpdateForecastWorker.UPDATE_FORECAST_WORKER_NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                updateWorker
            )
    }

    fun oneTimeUpdateForecastAllCity() {
        val updateWorker =
            OneTimeWorkRequestBuilder<UpdateForecastWorker>()
                .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                UpdateForecastWorker.UPDATE_FORECAST_WORKER_NAME,
                ExistingWorkPolicy.REPLACE,
                updateWorker
            )
    }


    suspend fun requestForecastAllCity(): List<Forecast> {
        val listForecast = mutableListOf<Forecast>()
        return withContext(Dispatchers.IO) {
            try {
                DefaultListCity.listCity.forEach { city ->
                    Networking.forecastApi.requestWeatherForecastByCityId(city.id)
                        .also { forecast ->
                            Log.d(TAG, "requestForecastAllCity: forecast $forecast")
                            databaseForecastRepository.saveForecastInDatabase(forecast)
                        }
                }
                listForecast
            } catch (t: IOException) {
                Log.d(TAG, "requestForecastAllCity: ERROR t:$t")
                emptyList()
            }
        }
    }

    suspend fun requestHistoryMonth(cityId: Int, month: Int): HistoryData {
        return withContext(Dispatchers.IO) {
            Log.d(TAG, "requestHistoryMonth: ")
            Networking.historyApi.requestStatisticalMonthlyAggregation(cityId, month).result
        }
    }

    suspend fun requestHistoryDay(cityId: Int, month: Int, day: Int): HistoryData {
        return withContext(Dispatchers.IO) {
            Log.d(TAG, "requestHistoryDay: ")
            Networking.historyApi.requestStatisticalDailyAggregation(cityId, month, day).result
        }
    }

    companion object {
        const val TAG = "RemoteRepo_Logging"
    }
}
