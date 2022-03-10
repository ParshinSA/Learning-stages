package com.example.weatherapplication.data.repositories.repo_interface

import com.example.weatherapplication.data.models.report.DataHistory
import com.example.weatherapplication.ui.common.ReportPeriods
import io.reactivex.Completable

interface MemoryRepository {
    fun saveReportInCacheDirection(nameCity: String, period: ReportPeriods, report: DataHistory): Completable
    fun openReportFromCacheDir(): String
}
