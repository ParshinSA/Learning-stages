package com.example.weatherapplication.domain.interactors

import android.util.Log
import androidx.work.*
import com.example.weatherapplication.data.database.models.city.City
import com.example.weatherapplication.data.database.models.forecast.Forecast
import com.example.weatherapplication.data.reporitories.ForecastRepository
import com.example.weatherapplication.domain.interactors.interactors_interface.ForecastInteractor
import com.example.weatherapplication.workers.UpdateForecastWorker
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ForecastInteractorImpl @Inject constructor(
    private val workManager: WorkManager,
    private val repository: ForecastRepository,
    private val disposable: CompositeDisposable
) : ForecastInteractor {

    override fun updateForecast(): Completable {
        return Completable.create { subscribe ->
            disposable.add(
                getListCity().subscribe({ listCity ->

                    Log.d(TAG, "updateForecast: customCityList = $listCity")
                    disposable.add(
                        requestForecast(listCity).subscribe({ listForecast ->

                            Log.d(TAG, "updateForecast: listForecast = $listForecast")
                            disposable.add(
                                saveForecastToDatabase(listForecast).subscribe({

                                    Log.d(TAG, "updateForecast: save completed")
                                    saveTimeUpdate()
                                    subscribe.onComplete()
                                }, {
                                    subscribe.onError(it)
                                    Log.d(TAG, "updateForecast: error save $it")
                                })
                            )
                        }, {
                            subscribe.onError(it)
                            Log.d(TAG, "updateForecast: error save forecast $it")
                        })
                    )
                }, {
                    subscribe.onError(it)
                    Log.d(TAG, "updateForecast: error get list city $it")
                })
            )
        }
    }

    private fun getListCity(): Single<List<City>> {
        return repository.getCustomCities()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())

    }

    private fun saveTimeUpdate() {
        val currentTimestamp = System.currentTimeMillis()
        repository.saveLastUpdateTime(currentTimestamp)
    }

    override fun oneTimeUpdateForecasts() {
        workManager.enqueueUniqueWork(
            UpdateForecastWorker.UPDATE_FORECAST_WORKER_NAME,
            ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequestBuilder<UpdateForecastWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build()
                )
                .build()
        )
    }

    override fun periodUpdateForecasts() {
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

    private fun requestForecast(listCity: List<City>): Observable<List<Forecast>> {
        return Observable.fromIterable(listCity)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMap { city ->
                repository.requestForecast(
                    latitude = city.latitude,
                    longitude = city.longitude,
                )
            }
            .buffer(listCity.size)
    }

    private fun saveForecastToDatabase(listForecast: List<Forecast>): Completable {
        return Observable.fromIterable(listForecast)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMapCompletable { forecast ->
                repository.addForecastInDatabase(forecast = forecast)
            }
    }

    override fun subscribeToForecastDatabase(): Observable<List<Forecast>> {
        return repository.subscribeToForecastDatabase()
    }

    override fun getLastUpdateTime(): Long {
        return repository.getLastUpdateTime()
    }

    companion object {
        const val TAG = "ForecastInt_Logging"
    }
}