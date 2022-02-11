package com.example.weatherapplication.data.repositories

import android.content.Context
import android.util.Log
import androidx.work.*
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.models.report.Field
import com.example.weatherapplication.data.models.report.HistoryData
import com.example.weatherapplication.data.objects.AppDisposable
import com.example.weatherapplication.data.objects.CustomCities
import com.example.weatherapplication.data.retrofit.Networking
import com.example.weatherapplication.utils.calculateDayStepDay
import com.example.weatherapplication.utils.calculateMonthStepDay
import com.example.weatherapplication.utils.calculateMonthStepMonth
import com.example.weatherapplication.workers.UpdateForecastWorker
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
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

    fun requestForecastAllCity() {
        Log.d(TAG, "requestForecastAllCity: list city: ${CustomCities.listCitiesLiveData.value}")
        AppDisposable.disposableList.add(
            Observable.fromIterable(CustomCities.listCitiesLiveData.value)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap {
                    Log.d(TAG, "requestForecastAllCity: request lat=${it.lat}, lon=${it.lon}")
                    Networking.forecastApi.requestForecast(it.lat, it.lon)
                }
                .subscribe({
                    Log.d(TAG, "requestForecastAllCity: response $it")
                    saveForecastInDatabase(it)
                }, {
                    Log.d(TAG, "requestForecastAllCity: ERROR $it")
                })
        )
    }

    private fun saveForecastInDatabase(forecast: Forecast) =
        databaseForecastRepository.saveForecastInDatabase(forecast)

    fun requestHistory(forecast: Forecast, period: String): Observable<HistoryData> {
        return when (period) {
            "Десять дней" -> requestHistoryDay(forecast, 10)
            "Один месяц" -> requestHistoryMonth(forecast, 1)
            "Три месяца" -> requestHistoryMonth(forecast, 3)
            else -> error("Incorrect period")
        }
    }

    private fun requestHistoryMonth(forecast: Forecast, numberMonth: Int): Observable<HistoryData> {
        return Observable.fromIterable(0 until numberMonth)
            .observeOn(Schedulers.io())
            .doOnNext { Log.d(TAG, "request month in ${Thread.currentThread().name}") }
            .flatMap { step ->
                Networking.historyApi.requestHistoryMonth(
                    forecast.coordination.lat,
                    forecast.coordination.lon,
                    calculateMonthStepMonth(step) // step 30 day
                )
            }
            .map { it.result }
            .buffer(numberMonth)
            .map { calculate(it.toList()) }
    }

    private fun requestHistoryDay(forecast: Forecast, numberDay: Int): Observable<HistoryData> {
        return Observable.fromIterable(0 until numberDay)
            .observeOn(Schedulers.io())
            .doOnNext { Log.d(TAG, "request day in ${Thread.currentThread().name}") }
            .flatMap { step ->
                Networking.historyApi.requestHistoryDay(
                    forecast.coordination.lat,
                    forecast.coordination.lon,
                    calculateDayStepDay(step),
                    calculateMonthStepDay(step)
                )
            }
            .map { it.result }
            .buffer(numberDay)
            .map { calculate(it.toList()) }
    }

    private fun calculate(list: List<HistoryData>): HistoryData {
        var resultItem = HistoryData(
            temp = Field(0.0),
            pressure = Field(0.0),
            humidity = Field(0.0),
            wind = Field(0.0),
            precipitation = Field(0.0)
        )

        for (item in list) {
            resultItem = HistoryData(
                temp = Field(resultItem.temp.median + item.temp.median),
                pressure = Field(resultItem.pressure.median + item.pressure.median),
                humidity = Field(resultItem.humidity.median + item.humidity.median),
                wind = Field(resultItem.wind.median + item.wind.median),
                precipitation = Field(resultItem.precipitation.median + item.precipitation.median)
            )
        }

        resultItem = HistoryData(
            temp = Field(resultItem.temp.median / list.size),
            pressure = Field(resultItem.pressure.median / list.size),
            humidity = Field(resultItem.humidity.median / list.size),
            wind = Field(resultItem.wind.median / list.size),
            precipitation = Field(resultItem.precipitation.median / list.size),
        )

        return resultItem
    }

    fun searchCity(userInput: String): Single<List<City>> {
        Log.d(TAG, "searchCity: $userInput")
        return Networking.coordinationApi.getCoordinationByNameCity(userInput)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }


    companion object {
        const val TAG = "RemoteRepo_Logging"
    }
}

