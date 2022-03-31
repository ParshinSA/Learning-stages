package com.example.weatherapplication.presentation.viewmodels.viewmodel_classes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.domain.interactors.interactors_interface.ReportInteractor
import com.example.weatherapplication.domain.models.report.DomainRequestReport
import com.example.weatherapplication.presentation.common.SingleLiveEvent
import com.example.weatherapplication.presentation.models.city.UiCity
import com.example.weatherapplication.presentation.models.report.UiRequestReport
import com.example.weatherapplication.presentation.models.report.convertToDomainRequestReport
import com.example.weatherapplication.presentation.models.report.nested_request_report.ReportingPeriod
import com.example.weatherapplication.presentation.viewmodels.BaseViewModel
import io.reactivex.schedulers.Schedulers

class ReportViewModel(
    private val interactor: ReportInteractor,
) : BaseViewModel() {

    private val isLoadingMutableLiveData = MutableLiveData(false)
    val isLoadingLiveData: LiveData<Boolean>
        get() = isLoadingMutableLiveData

    private val generateReportIsCompletedSingleLiveEvent = SingleLiveEvent<Boolean>()
    val generateReportIsCompletedLiveData: LiveData<Boolean>
        get() = generateReportIsCompletedSingleLiveEvent

    private val reportFileMutableLiveData = MutableLiveData<String>()
    val reportFileLiveData: LiveData<String>
        get() = reportFileMutableLiveData

    private val errorMessageMutableLiveData = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = errorMessageMutableLiveData

    fun generateReport(uiCity: UiCity, reportingPeriod: ReportingPeriod) {
        isLoadingMutableLiveData.postValue(true)

        val uiRequestReport = createUiRequestReport(uiCity, reportingPeriod)
        compositeDisposable.add(
            interactor.generateReport(uiRequestReport)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({
                    generateReportIsCompletedSingleLiveEvent.postValue(true)
                    isLoadingMutableLiveData.postValue(false)
                }, {
                    Log.d(TAG, "generateReport: error $it")
                    isLoadingMutableLiveData.postValue(false)
                }
                ))
    }

    fun openReport() {
        reportFileMutableLiveData.postValue(
            interactor.openReportFromCache().reportString
        )
    }

    private fun createUiRequestReport(
        uiCity: UiCity,
        reportingPeriod: ReportingPeriod
    ): DomainRequestReport {
        return UiRequestReport(
            cityName = uiCity.cityName,
            latitude = uiCity.latitude,
            longitude = uiCity.longitude,
            reportingPeriod = reportingPeriod
        ).convertToDomainRequestReport()
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
