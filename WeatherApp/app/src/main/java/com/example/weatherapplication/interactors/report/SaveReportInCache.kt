package com.example.weatherapplication.interactors.report

import com.example.weatherapplication.data.reporitories.ReportRepository

class SaveReportInCache(
    private val repository: ReportRepository
) {

    operator fun invoke(report: String) {
        repository.saveInCache(report = report)
    }

}