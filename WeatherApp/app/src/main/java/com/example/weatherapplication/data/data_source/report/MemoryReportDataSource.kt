package com.example.weatherapplication.data.data_source.report

import io.reactivex.Completable

interface MemoryReportDataSource {

    fun saveInCache(report: String): Completable
    fun openFromCache(): String

}