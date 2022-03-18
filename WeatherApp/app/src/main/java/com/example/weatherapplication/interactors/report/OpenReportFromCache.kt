package com.example.weatherapplication.interactors.report

import com.example.weatherapplication.data.reporitories.ReportRepository

class OpenReportFromCache(
    private val repository: ReportRepository
) {

    fun execute() = repository.openFromCache()

}