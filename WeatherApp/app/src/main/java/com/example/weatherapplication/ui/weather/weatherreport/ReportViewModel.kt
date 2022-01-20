package com.example.weatherapplication.ui.weather.weatherreport

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.data.models.report.Field
import com.example.weatherapplication.data.models.report.HistoryData
import com.example.weatherapplication.data.repositories.MemoryRepository
import com.example.weatherapplication.data.repositories.RemoteRepository
import com.example.weatherapplication.utils.SingleLiveEvent
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat

class ReportViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val remoteRepo = RemoteRepository(application)
    private val memoryRepository = MemoryRepository(application)

    private val isLoadingMutableLiveData = MutableLiveData(false)
    val isLoadingLiveData: LiveData<Boolean>
        get() = isLoadingMutableLiveData

    private val loadingCompleteSingleLiveEvent = SingleLiveEvent<Boolean>()
    val loadingComplete: LiveData<Boolean>
        get() = loadingCompleteSingleLiveEvent

    private val reportFileMutableLiveData = MutableLiveData<String>()
    val reportFileLiveData: LiveData<String>
        get() = reportFileMutableLiveData

    private val errorMessageMutableLiveData = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = errorMessageMutableLiveData

    fun generateReport(nameCity: String, idCity: Int, period: String) {
        Log.d(TAG, "generateReport: start")
        viewModelScope.launch() {
            isLoadingMutableLiveData.postValue(true)
            val historyList = requestHistory(idCity, period)

            if (historyList.isNotEmpty()) {
                val calculateMedian = calculateMedian(historyList)
                Log.d(TAG, "generateReport: $calculateMedian")
                memoryRepository.saveReportInCacheDirection(nameCity, period, calculateMedian)
                loadingCompleteSingleLiveEvent.postValue(true)
            } else {
                errorMessageMutableLiveData.postValue("Что-то пошло не так...")
                loadingCompleteSingleLiveEvent.postValue(false)
            }

            isLoadingMutableLiveData.postValue(false)
        }
    }

    private suspend fun requestHistory(idCity: Int, period: String): List<HistoryData> {
        return try {
            when (period) {
                "Десять дней" -> requestHistoryDay(idCity, 10)
                "Один месяц" -> requestHistoryMonth(idCity, 1)
                "Три месяца" -> requestHistoryMonth(idCity, 3)
                else -> {
                    Log.d(TAG, "generateReport: Incorrect period $period")
                    errorMessageMutableLiveData.postValue("Что-то пошло не так...")
                    emptyList()
                }
            }
        } catch (e: IOException) {
            Log.d(TAG, "generateReport: error history list ${e.message}")
            errorMessageMutableLiveData.postValue("Что-то пошло не так...")
            emptyList()
        }
    }

    private suspend fun requestHistoryDay(idCity: Int, numberDays: Int): List<HistoryData> {
        Log.d(TAG, "requestHistoryDay: ")
        val currentTimeStamp = System.currentTimeMillis()
        var currentDay = SimpleDateFormat("dd").format(currentTimeStamp).toInt()
        val currentMonth = SimpleDateFormat("MM").format(currentTimeStamp).toInt()
        val historyList = mutableListOf<HistoryData>()

        for (i in 1..numberDays) {
            historyList.add(
                remoteRepo.requestHistoryDay(
                    idCity,
                    currentMonth,
                    currentDay
                )
            )
            currentDay--
        }

        return historyList.toList()
    }

    private suspend fun requestHistoryMonth(idCity: Int, numberMonth: Int): List<HistoryData> {
        Log.d(TAG, "requestHistoryMonth: ")
        val currentTimeStamp = System.currentTimeMillis()
        var currentMonth = SimpleDateFormat("MM").format(currentTimeStamp).toInt()
        val historyList = mutableListOf<HistoryData>()

        for (i in 1..numberMonth) {
            historyList.add(remoteRepo.requestHistoryMonth(idCity, currentMonth))
            currentMonth--
            if (currentMonth == 0) currentMonth = 12
            Log.d(TAG, "requestHistoryMonth: i:$i, his: $historyList")
        }

        return historyList.toList()
    }

    fun openReport() {
        reportFileMutableLiveData.postValue(
            memoryRepository.openReportFromCacheDir()
        )
    }

    private fun calculateMedian(historyList: List<HistoryData>): HistoryData {

        var tempMedian = 0f
        var humidityMedian = 0f
        var precipitationMedian = 0f
        var pressureMedian = 0f
        var windMedian = 0f

        for (i in 0..historyList.lastIndex) {
            tempMedian += historyList[i].temp.median
            pressureMedian += historyList[i].pressure.median
            humidityMedian += historyList[i].humidity.median
            windMedian += historyList[i].wind.median
            precipitationMedian += historyList[i].precipitation.median
        }
        val delimiter = historyList.size

        return HistoryData(
            temp = Field(tempMedian / delimiter),
            pressure = Field(pressureMedian / delimiter),
            humidity = Field(humidityMedian / delimiter),
            wind = Field(windMedian / delimiter),
            precipitation = Field(precipitationMedian / delimiter),
        )
    }

    override fun onCleared() {
        Log.d(TAG, "onCleared: ")
        super.onCleared()
    }

    companion object {
        const val TAG = "ReportVM_SystemLogging"
    }
}
