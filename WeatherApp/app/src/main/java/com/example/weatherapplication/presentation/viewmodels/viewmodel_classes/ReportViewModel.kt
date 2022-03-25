package com.example.weatherapplication.presentation.viewmodels.viewmodel_classes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapplication.domain.interactors.interactors_interface.ReportInteractor
import com.example.weatherapplication.presentation.common.SingleLiveEvent
import com.example.weatherapplication.presentation.models.city.UiCityDto
import com.example.weatherapplication.presentation.models.report.ReportPeriod
import com.example.weatherapplication.presentation.models.report.request.UiRequestReportDto
import com.example.weatherapplication.presentation.viewmodels.BaseViewModel

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

    fun generateReport(uiCityDto: UiCityDto, reportPeriod: ReportPeriod) {
        isLoadingMutableLiveData.postValue(true)

        val uiRequestReportDto = createUiRequestReport(uiCityDto, reportPeriod)
        compositeDisposable.add(
            interactor.generateReport(uiRequestReportDto).subscribe({
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
        uiCityDto: UiCityDto,
        reportPeriod: ReportPeriod
    ): UiRequestReportDto {
        return UiRequestReportDto(
            cityName = uiCityDto.cityName,
            latitude = uiCityDto.latitude,
            longitude = uiCityDto.longitude,
            reportPeriod = reportPeriod
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
