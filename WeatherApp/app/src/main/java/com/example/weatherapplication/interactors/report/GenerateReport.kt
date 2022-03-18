package com.example.weatherapplication.interactors.report

import com.example.weatherapplication.app.framework.database.models.report.WeatherStatistic
import com.example.weatherapplication.data.reporitories.ReportRepository
import com.example.weatherapplication.domain.ReportingPeriod

class GenerateReport(
    private val repository: ReportRepository
) {

    fun generateReport(
        nameCity: String,
        reportingPeriod: ReportingPeriod,
        statisticData: WeatherStatistic
    ) {
        repository.generateReport(
            nameCity = nameCity,
            reportingPeriod = reportingPeriod,
            statisticData = statisticData
        )
    }

}