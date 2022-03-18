package com.example.weatherapplication.app.presentation.viewmodels.viewmodel_classes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.app.common.SingleLiveEvent
import com.example.weatherapplication.data.data_source.forecast.RemoteForecastDataSource
import com.example.weatherapplication.app.framework.database.models.forecast.Forecast
import com.example.weatherapplication.app.framework.database.models.report.WeatherStatistic
import com.example.weatherapplication.app.framework.database.models.report.FieldValue
import com.example.weatherapplication.app.framework.database.models.report.StatisticalWeather
import com.example.weatherapplication.app.framework.RemoteRepository
import com.example.weatherapplication.domain.ReportingPeriod
import com.example.weatherapplication.app.presentation.viewmodels.BaseViewModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class ReportViewModel(
    private val remoteRepo: RemoteForecastDataSource,
    private val memoryRepo: ReportDataSource
) : BaseViewModel() {

    private val isLoadingMutableLiveData = MutableLiveData(false)
    val isLoadingLiveData: LiveData<Boolean>
        get() = isLoadingMutableLiveData

    private val isSaveReportSingleLiveEvent = SingleLiveEvent<Boolean>()
    val isSaveReportLiveData: LiveData<Boolean>
        get() = isSaveReportSingleLiveEvent

    private val reportFileMutableLiveData = MutableLiveData<String>()
    val reportFileLiveData: LiveData<String>
        get() = reportFileMutableLiveData

    private val errorMessageMutableLiveData = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = errorMessageMutableLiveData

    fun generateReport(forecast: Forecast, period: ReportingPeriod) {
        Log.d(TAG, "generateReport: start")
        isLoadingMutableLiveData.postValue(true)

        compositeDisposable.add(
            requestHistory(forecast, period)
                .subscribe(
                    {
                        Log.d(TAG, "generateReport: $it")
                        saveReport(forecast, period, it)
                    },
                    {
                        Log.d(TAG, "generateReport: ERROR $it")
                    }, {
                        isLoadingMutableLiveData.postValue(false)
                    }
                )
        )
    }

    private fun requestHistory(
        forecast: Forecast,
        period: ReportingPeriod
    ): Observable<WeatherStatistic> {
        return Observable.fromIterable(0 until period.quantity)
            .observeOn(Schedulers.io())
            .flatMap { step ->
                if (period.stringQuantity == ReportingPeriod.TEN_DAYS.stringQuantity) {
                    remoteRepo.requestStatisticalDataStepDay(forecast, step)
                } else {
                    remoteRepo.requestStatisticalDataStepMonth(forecast, step)
                }
            }
            .map { statistical: StatisticalWeather ->
                Log.d(RemoteRepository.TAG, "requestHistory: $statistical")
                statistical.dataHistory
            }
            .buffer(period.quantity)
            .map { listHistoryData ->
                calculateSumHistoryData(listHistoryData)
            }
            .map { sumItemHistoryData ->
                calculateAveragesHistoryData(sumItemHistoryData, period)
            }
    }

    private fun calculateSumHistoryData(list: List<WeatherStatistic>): WeatherStatistic {
        return Observable.fromIterable(list)
            .scan { accumulator: WeatherStatistic, itemDataHistory: WeatherStatistic ->
                WeatherStatistic(
                    temperature = FieldValue(accumulator.temperature.medianValue + itemDataHistory.temperature.medianValue),
                    pressure = FieldValue(accumulator.pressure.medianValue + itemDataHistory.pressure.medianValue),
                    humidity = FieldValue(accumulator.humidity.medianValue + itemDataHistory.humidity.medianValue),
                    wind = FieldValue(accumulator.wind.medianValue + itemDataHistory.wind.medianValue),
                    precipitation = FieldValue(accumulator.precipitation.medianValue + itemDataHistory.precipitation.medianValue)
                )
            }.blockingLast()
    }

    private fun calculateAveragesHistoryData(
        sumItemDataHistory: WeatherStatistic,
        period: ReportingPeriod
    ): WeatherStatistic {
        return WeatherStatistic(
            temperature = FieldValue(sumItemDataHistory.temperature.medianValue / period.quantity),
            pressure = FieldValue(sumItemDataHistory.pressure.medianValue / period.quantity),
            humidity = FieldValue(sumItemDataHistory.humidity.medianValue / period.quantity),
            wind = FieldValue(sumItemDataHistory.wind.medianValue / period.quantity),
            precipitation = FieldValue(sumItemDataHistory.precipitation.medianValue / period.quantity),
        )
    }

    private fun saveReport(forecast: Forecast, period: ReportingPeriod, medianSata: WeatherStatistic) {
        compositeDisposable.add(
            memoryRepo.saveReportInCacheDirection(forecast.cityName, period, medianSata)
                .subscribe({
                    Log.d(TAG, "saveReport: success")
                    isSaveReportSingleLiveEvent.postValue(true)
                }, {
                    Log.d(TAG, "saveReport: error $it")
                    isSaveReportSingleLiveEvent.postValue(false)
                })
        )
    }

    fun openReport() {
        reportFileMutableLiveData.postValue(
            memoryRepo.openReportFromCacheDir()
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
        Log.d(TAG, "onCleared: ")
        super.onCleared()
    }

    companion object {
        const val TAG = "ReportVM_SystemLogging"
    }
}
