package com.example.weatherapplication.ui.weather.weatherreport

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapplication.data.models.report.HistoryData
import com.example.weatherapplication.data.models.report.Field
import com.example.weatherapplication.data.repositories.MemoryRepository
import com.example.weatherapplication.data.repositories.RemoteWeatherHistoryRepository
import com.example.weatherapplication.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat

class ReportViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val remoteHistoryRepository = RemoteWeatherHistoryRepository()
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

    fun generateReport(nameCity: String, idCity: Int, period: String) {
        viewModelScope.launch(Dispatchers.IO) {
            isLoadingMutableLiveData.postValue(true)
            val historyList =
                try {
                    when (period) {
                        "Десять дней" -> requestHistoryDay(idCity, 10)
                        "Один месяц" -> requestHistoryMonth(idCity, 1)
                        "Три месяца" -> requestHistoryMonth(idCity, 3)
                        else -> error("Incorrect period $period")

                    }
                } catch (e: IOException) {
                    error("error history list ${e.message}")
                }

            val calculateMedian = calculateMedian(historyList)
            Log.d("SystemLogging", "$calculateMedian")

            memoryRepository.saveReportInCacheDirection(nameCity, period, calculateMedian)
            isLoadingMutableLiveData.postValue(false)
            loadingCompleteSingleLiveEvent.postValue(true)
        }
    }

    private suspend fun requestHistoryDay(idCity: Int, numberDays: Int): List<HistoryData> {
        val currentTimeStamp = System.currentTimeMillis()
        var currentDay = SimpleDateFormat("dd").format(currentTimeStamp).toInt()
        val currentMonth = SimpleDateFormat("MM").format(currentTimeStamp).toInt()
        val historyList = mutableListOf<HistoryData>()

        for (i in 1..numberDays) {
            historyList.add(
                remoteHistoryRepository.requestHistoryDay(
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
        val currentTimeStamp = System.currentTimeMillis()
        var currentMonth = SimpleDateFormat("MM").format(currentTimeStamp).toInt()
        val historyList = mutableListOf<HistoryData>()

        for (i in 1..numberMonth) {
            historyList.add(remoteHistoryRepository.requestHistoryMonth(idCity, currentMonth))
            currentMonth--
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
}
