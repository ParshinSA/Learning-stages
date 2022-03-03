package com.example.weatherapplication.data.repositories.repo_interface

import com.example.weatherapplication.data.models.report.DataHistory
import com.example.weatherapplication.ui.weather.report.Period
import io.reactivex.Completable

interface MemoryRepository {
    fun saveReportInCacheDirection(nameCity: String, period: Period, report: DataHistory): Completable
    fun openReportFromCacheDir(): String
}
