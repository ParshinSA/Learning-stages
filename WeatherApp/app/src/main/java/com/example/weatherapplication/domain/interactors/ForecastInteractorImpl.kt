package com.example.weatherapplication.domain.interactors

import android.content.Context
import androidx.work.*
import com.example.weatherapplication.domain.interactors.interactors_interface.ForecastInteractor
import com.example.weatherapplication.domain.models.city.response.DomainCityDto
import com.example.weatherapplication.domain.models.forecast.DomainForecastDto
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

    private fun getListCity(): Observable<List<DomainCityDto>> {
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

    private fun requestForecast(listDomainCityDto: List<DomainCityDto>): Observable<List<DomainForecastDto>> {
        return Observable.fromIterable(listDomainCityDto)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMap { domainCityDto ->
                repository.requestForecast(domainCityDto = domainCityDto)
            }
            .buffer(listDomainCityDto.size)
    }

    private fun saveForecastToDatabase(listDomainForecastDto: List<DomainForecastDto>): Completable {
        return Observable.fromIterable(listDomainForecastDto)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMapCompletable { domainForecastDto ->
                repository.addForecastInDatabase(domainForecastDto)
            }
    }

    override fun getListForecastFromDatabase(): Observable<List<DomainForecastDto>> {
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