package com.example.weatherapplication.data.reporitories

import com.example.weatherapplication.app.framework.database.models.report.WeatherStatistic
import com.example.weatherapplication.data.data_source.report.GenerateReportDataSource
import com.example.weatherapplication.data.data_source.report.MemoryReportDataSource
import com.example.weatherapplication.domain.ReportingPeriod

class ReportRepository(
    private val memoryDataSource: MemoryReportDataSource,
    private val generateReportDataSource: GenerateReportDataSource
) {

    fun generateReport(
        nameCity: String,
        reportingPeriod: ReportingPeriod,
        statisticData: WeatherStatistic
    ) {
        generateReportDataSource.generateReport(
            nameCity = nameCity,
            reportingPeriod = reportingPeriod,
            statisticData = statisticData
        )
    }

    fun saveInCache(report: String) {
        memoryDataSource.saveInCache(report = report)
    }

    fun openFromCache() = memoryDataSource.openFromCache()
}