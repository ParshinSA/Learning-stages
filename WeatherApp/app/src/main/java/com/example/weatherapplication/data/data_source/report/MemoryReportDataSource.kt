package com.example.weatherapplication.data.data_source.report

import io.reactivex.Completable

interface MemoryReportDataSource {
    fun openReportFromCache(): String
    fun saveInCache(report: String): Completable
}