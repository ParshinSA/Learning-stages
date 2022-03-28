package com.example.weatherapplication.domain.interactors

import android.content.Context
import android.util.Log
import androidx.work.*
import com.example.weatherapplication.domain.interactors.interactors_interface.ForecastInteractor
import com.example.weatherapplication.domain.models.city.DomainCity
import com.example.weatherapplication.domain.models.forecast.DomainForecast
import com.example.weatherapplication.domain.models.update_time.DomainLastTimeUpdate
import com.example.weatherapplication.domain.repository.ForecastRepository
import com.example.weatherapplication.presentation.common.AppState
import com.example.weatherapplication.workers.UpdateForecastWorker
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ForecastInteractorImpl @Inject constructor(
    private val repository: ForecastRepository,
    private val context: Context,
    private val appState: AppState
) : ForecastInteractor {


    override fun updateForecast() {
        if (appState.isCollapsed) periodUpdateForecasts() else oneTimeUpdateForecasts()
    }

    override fun updateForecastFromService(): Completable {
        return getListCity()
            .flatMap { listDomainCityDto ->
                requestForecast(listDomainCityDto)
            }
            .flatMapCompletable { listDomainForecastDto ->
                saveTimeUpdate()
                saveForecastToDatabase(listDomainForecastDto)
            }
    }

    private fun getListCity(): Observable<List<DomainCity>> {
        return repository.getListCity()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
    }

    private fun saveTimeUpdate() {
        repository.saveLastUpdateTime()
    }

    private fun oneTimeUpdateForecasts() {
        WorkManager.getInstance(context).enqueueUniqueWork(
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

    private fun periodUpdateForecasts() {
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
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

    private fun requestForecast(listDomainCity: List<DomainCity>): Observable<List<DomainForecast>> {
        return Observable.fromIterable(listDomainCity)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMap { domainCityDto ->
                repository.requestForecast(domainCity = domainCityDto)
            }
            .buffer(listDomainCity.size)
    }

    private fun saveForecastToDatabase(listDomainForecast: List<DomainForecast>): Completable {
        return Observable.fromIterable(listDomainForecast)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMapCompletable { domainForecastDto ->
                repository.addForecastInDatabase(domainForecastDto)
            }
    }

    override fun getListForecastFromDatabase(): Observable<List<DomainForecast>> {
        return repository.getListForecastFromDatabase()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .debounce(500, TimeUnit.MILLISECONDS)
    }

    override fun getLastUpdateTime(): DomainLastTimeUpdate {
        return repository.getLastUpdateTime()
    }

    companion object {
        const val TAG = "ForecastInt_Logging"
    }
}