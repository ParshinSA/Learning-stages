package com.example.weatherapplication.data.repositories

import android.content.Context
import android.net.LocalSocket
import android.util.Log
import androidx.work.*
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.models.report.Field
import com.example.weatherapplication.data.models.report.HistoryData
import com.example.weatherapplication.data.models.report.HistoryForecast
import com.example.weatherapplication.data.objects.CustomCities
import com.example.weatherapplication.data.retrofit.Networking
import com.example.weatherapplication.ui.weather.weather_report.Period
import com.example.weatherapplication.ui.weather.weather_report.Period.TEN_DAYS
import com.example.weatherapplication.workers.UpdateForecastWorker
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class RemoteRepository(
    private val context: Context
) {
    private val databaseForecastRepository = ForecastDbRepository()

    fun periodUpdateForecastAllCityList() {
        Log.d(TAG, "periodUpdateForecastAllCityList: start")
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
        Log.d(TAG, "oneTimeUpdateForecastAllCity: start")
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

    fun requestForecastAllCity(): Observable<Forecast> {
        return Observable.fromIterable(CustomCities.listCitiesLiveData.value)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMap {
                Networking.forecastApi.requestForecast(it.lat, it.lon)
            }
    }

    fun saveForecastInDatabase(forecast: Forecast) =
        databaseForecastRepository.saveForecastInDatabase(forecast)

    fun requestHistory(
        forecast: Forecast,
        period: Period
    ): Observable<HistoryData> {
        return Observable.fromIterable(0 until period.quantity)
            .observeOn(Schedulers.io())
            .flatMap { step ->
                if (period.stringQuantity == TEN_DAYS.stringQuantity) {
                    requestHistoryDay(forecast, step)
                } else {
                    requestHistoryMonth(forecast,step)
                }
            }
            .map { history: HistoryForecast ->
                Log.d(TAG, "requestHistory: $history")
                history.historyData
            }
            .buffer(period.quantity)
            .map { listHistoryData ->
                calculateSumHistoryData(listHistoryData)
            }
            .map { sumItemHistoryData ->
                calculateAveragesHistoryData(sumItemHistoryData, period)
            }
    }

    private fun requestHistoryMonth(forecast: Forecast, step: Int): Observable<HistoryForecast> {
        return Networking.historyApi.requestHistoryMonth(
            forecast.coordination.lat,
            forecast.coordination.lon,
            calculateMonthStepMonth(step) // step 30 day
        )
    }

    private fun requestHistoryDay(forecast: Forecast, step: Int): Observable<HistoryForecast> {
       return Networking.historyApi.requestHistoryDay(
            forecast.coordination.lat,
            forecast.coordination.lon,
            calculateDayStepDay(step),
            calculateMonthStepDay(step)
        )
    }

    private fun calculateSumHistoryData(list: List<HistoryData>): HistoryData {
        return Observable.fromIterable(list)
            .scan { accumulator: HistoryData, itemHistory: HistoryData ->
                HistoryData(
                    temp = Field(accumulator.temp.median + itemHistory.temp.median),
                    pressure = Field(accumulator.pressure.median + itemHistory.pressure.median),
                    humidity = Field(accumulator.humidity.median + itemHistory.humidity.median),
                    wind = Field(accumulator.wind.median + itemHistory.wind.median),
                    precipitation = Field(accumulator.precipitation.median + itemHistory.precipitation.median)
                )
            }.blockingLast()
    }

    private fun calculateAveragesHistoryData(sumItemHistoryData: HistoryData, period: Period): HistoryData {
        return HistoryData(
            temp = Field(sumItemHistoryData.temp.median / period.quantity),
            pressure = Field(sumItemHistoryData.pressure.median / period.quantity),
            humidity = Field(sumItemHistoryData.humidity.median / period.quantity),
            wind = Field(sumItemHistoryData.wind.median / period.quantity),
            precipitation = Field(sumItemHistoryData.precipitation.median / period.quantity),
        )
    }

    fun searchCity(userInput: String): Single<List<City>> {
        Log.d(TAG, "searchCity: $userInput")
        return Networking.coordinationApi.getCoordinationByNameCity(userInput)
    }

    private fun calculateDayStepDay(step: Int): Int {
        return SimpleDateFormat("dd", Locale("ru"))
            .format(System.currentTimeMillis() - 86400000 * step).toInt()
    }

    private fun calculateMonthStepDay(step: Int): Int {
        return SimpleDateFormat("MM", Locale("ru"))
            .format(System.currentTimeMillis() - 86400000 * step).toInt()
    }

    private fun calculateMonthStepMonth(step: Int): Int {
        return SimpleDateFormat("MM", Locale("ru"))
            .format(System.currentTimeMillis() - 2592000000 * step).toInt()
    }

    companion object {
        const val TAG = "RemoteRepo_Logging"
    }
}

