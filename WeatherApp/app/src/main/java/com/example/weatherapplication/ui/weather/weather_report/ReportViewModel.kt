package com.example.weatherapplication.ui.weather.weather_report

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.models.report.HistoryData
import com.example.weatherapplication.data.repositories.MemoryRepository
import com.example.weatherapplication.data.repositories.RemoteRepository
import com.example.weatherapplication.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

class ReportViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val disposeBack = CompositeDisposable()

    private val remoteRepo = RemoteRepository(application)
    private val memoryRepository = MemoryRepository(application)

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

    fun generateReport(forecast: Forecast, period: Period) {
        Log.d(TAG, "generateReport: start")
        isLoadingMutableLiveData.postValue(true)

        disposeBack.add(
            remoteRepo.requestHistory(forecast, period)
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

    private fun saveReport(forecast: Forecast, period: Period, medianSata: HistoryData) {
        disposeBack.add(
            memoryRepository.saveReportInCacheDirection(forecast.cityName, period, medianSata)
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
            memoryRepository.openReportFromCacheDir()
        )
    }

    override fun onCleared() {
        disposeBack.clear()
        Log.d(TAG, "onCleared: ")
        super.onCleared()
    }

    companion object {
        const val TAG = "ReportVM_SystemLogging"
    }
}
