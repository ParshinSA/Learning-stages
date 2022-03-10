package com.example.weatherapplication.data.repositories.repo_implementation

import android.util.Log
import androidx.work.*
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.models.report.DataHistory
import com.example.weatherapplication.data.models.report.FieldValue
import com.example.weatherapplication.data.models.report.HistoryWeather
import com.example.weatherapplication.data.networking.api.CoordinationApi
import com.example.weatherapplication.data.networking.api.ForecastApi
import com.example.weatherapplication.data.networking.api.HistoryApi
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import com.example.weatherapplication.ui.common.ReportPeriods
import com.example.weatherapplication.ui.common.ReportPeriods.TEN_DAYS
import com.example.weatherapplication.workers.UpdateForecastWorker
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class RemoteRepositoryImpl(
    private val workManager: WorkManager,
    private val forecastApi: ForecastApi,
    private val historyApi: HistoryApi,
    private val coordinationApi: CoordinationApi
) : RemoteRepository {

    override fun oneTimeUpdateForecastAllCity() {
        Log.d(TAG, "oneTimeUpdateForecastAllCity: start")

        workManager.enqueueUniqueWork(
            UpdateForecastWorker.UPDATE_FORECAST_WORKER_NAME,
            ExistingWorkPolicy.REPLACE,

            OneTimeWorkRequestBuilder<UpdateForecastWorker>()
                .build()
        )
    }

    override fun requestForecastAllCity(cityList: List<City>): Observable<Forecast> {
        return Observable.fromIterable(cityList)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMap {
                forecastApi.requestForecast(it.latitude, it.longitude)
            }
    }

    override fun periodUpdateForecastAllCityList() {
        Log.d(TAG, "periodUpdateForecastAllCityList: start")
        workManager.enqueueUniquePeriodicWork(
            UpdateForecastWorker.UPDATE_FORECAST_WORKER_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,

            PeriodicWorkRequestBuilder<UpdateForecastWorker>(15, TimeUnit.MINUTES)
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()
        )
    }

    override fun requestHistory(
        forecast: Forecast,
        period: ReportPeriods
    ): Observable<DataHistory> {
        return Observable.fromIterable(0 until period.quantity)
            .observeOn(Schedulers.io())
            .flatMap { step ->
                if (period.stringQuantity == TEN_DAYS.stringQuantity) {
                    requestHistoryDay(forecast, step)
                } else {
                    requestHistoryMonth(forecast, step)
                }
            }
            .map { history: HistoryWeather ->
                Log.d(TAG, "requestHistory: $history")
                history.dataHistory
            }
            .buffer(period.quantity)
            .map { listHistoryData ->
                calculateSumHistoryData(listHistoryData)
            }
            .map { sumItemHistoryData ->
                calculateAveragesHistoryData(sumItemHistoryData, period)
            }
    }

    override fun searchCity(userInput: String): Single<List<City>> {
        Log.d(TAG, "searchCity: $userInput")
        return coordinationApi.getCoordinationByNameCity(userInput)
    }

    private fun requestHistoryMonth(forecast: Forecast, step: Int): Observable<HistoryWeather> {
        return historyApi.requestHistoryMonth(
            forecast.coordination.latitude,
            forecast.coordination.longitude,
            calculateMonthStepMonth(step) // step 30 day
        )
    }

    private fun requestHistoryDay(forecast: Forecast, step: Int): Observable<HistoryWeather> {
        return historyApi.requestHistoryDay(
            forecast.coordination.latitude,
            forecast.coordination.longitude,
            calculateDayStepDay(step),
            calculateMonthStepDay(step)
        )
    }

    private fun calculateSumHistoryData(list: List<DataHistory>): DataHistory {
        return Observable.fromIterable(list)
            .scan { accumulator: DataHistory, itemDataHistory: DataHistory ->
                DataHistory(
                    temperature = FieldValue(accumulator.temperature.medianValue + itemDataHistory.temperature.medianValue),
                    pressure = FieldValue(accumulator.pressure.medianValue + itemDataHistory.pressure.medianValue),
                    humidity = FieldValue(accumulator.humidity.medianValue + itemDataHistory.humidity.medianValue),
                    wind = FieldValue(accumulator.wind.medianValue + itemDataHistory.wind.medianValue),
                    precipitation = FieldValue(accumulator.precipitation.medianValue + itemDataHistory.precipitation.medianValue)
                )
            }.blockingLast()
    }

    private fun calculateAveragesHistoryData(
        sumItemDataHistory: DataHistory,
        period: ReportPeriods
    ): DataHistory {
        return DataHistory(
            temperature = FieldValue(sumItemDataHistory.temperature.medianValue / period.quantity),
            pressure = FieldValue(sumItemDataHistory.pressure.medianValue / period.quantity),
            humidity = FieldValue(sumItemDataHistory.humidity.medianValue / period.quantity),
            wind = FieldValue(sumItemDataHistory.wind.medianValue / period.quantity),
            precipitation = FieldValue(sumItemDataHistory.precipitation.medianValue / period.quantity),
        )
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

