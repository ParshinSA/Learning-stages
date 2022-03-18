package com.example.weatherapplication.data.data_source.report

import com.example.weatherapplication.app.framework.database.models.report.WeatherStatistic
import com.example.weatherapplication.domain.ReportingPeriod

interface GenerateReportDataSource {

    fun generateReport(
        nameCity: String,
        reportingPeriod: ReportingPeriod,
        statisticData: WeatherStatistic
    ): String

}