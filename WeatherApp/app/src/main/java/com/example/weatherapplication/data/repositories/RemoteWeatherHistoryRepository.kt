package com.example.weatherapplication.data.repositories

import com.example.weatherapplication.data.models.report.HistoryData
import com.example.weatherapplication.data.retrofit.Networking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class RemoteWeatherHistoryRepository {
    suspend fun requestHistoryMonth(cityId: Int, month: Int): HistoryData {
        return withContext(Dispatchers.IO) {
            Timber.d("requestHistoryMonth")
            Networking.historyApi.requestStatisticalMonthlyAggregation(cityId, month).result
        }
    }

    suspend fun requestHistoryDay(cityId: Int, month: Int, day: Int): HistoryData {
        return withContext(Dispatchers.IO) {
            Timber.d("requestHistoryDay")
            Networking.historyApi.requestStatisticalDailyAggregation(cityId, month, day).result
        }
    }
}