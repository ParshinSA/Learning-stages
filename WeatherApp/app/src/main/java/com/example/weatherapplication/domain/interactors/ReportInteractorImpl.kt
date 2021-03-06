package com.example.weatherapplication.domain.interactors

import com.example.weatherapplication.domain.interactors.interactors_interface.ReportInteractor
import com.example.weatherapplication.domain.models.report.DomainRequestReport
import com.example.weatherapplication.domain.models.report.DomainResponseReport
import com.example.weatherapplication.domain.models.report.DomainSaveReportString
import com.example.weatherapplication.domain.repository.ReportRepository
import com.example.weatherapplication.presentation.common.toStringDoubleFormat
import com.example.weatherapplication.presentation.models.report.nested_request_report.ReportingPeriod
import io.reactivex.Completable
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ReportInteractorImpl @Inject constructor(
    private val repository: ReportRepository
) : ReportInteractor {

    override fun generateReport(domainRequestReport: DomainRequestReport): Completable {
        return requestRemoteReport(domainRequestReport)
            .map { domainResponseReportDto ->
                convertReportDataToReportString(
                    cityName = domainRequestReport.cityName,
                    reportingPeriod = domainRequestReport.reportingPeriod,
                    domainResponseReport = domainResponseReportDto
                )
            }
            .flatMapCompletable { reportString ->
                saveInCache(reportString)
            }
    }

    private fun requestRemoteReport(
        domainRequestReport: DomainRequestReport
    ): Observable<DomainResponseReport> {
        return Observable.fromIterable(0 until domainRequestReport.reportingPeriod.quantity)
            .flatMap { stepTime ->

                if (domainRequestReport.reportingPeriod.stringQuantity == ReportingPeriod.TEN_DAYS.stringQuantity) {

                    val day = calculateDayStepDay(stepTime)
                    val month = calculateMonthStepDay(stepTime)

                    repository.requestReportToDay(
                        DomainRequestReport(
                            latitude = domainRequestReport.latitude,
                            longitude = domainRequestReport.longitude,
                            day = day,
                            month = month
                        )
                    )

                } else {
                    val month = calculateMonthStepMonth(stepTime)

                    repository.requestReportToMonth(
                        DomainRequestReport(
                            latitude = domainRequestReport.latitude,
                            longitude = domainRequestReport.longitude,
                            month = month
                        )
                    )
                }
            }
            .buffer(domainRequestReport.reportingPeriod.quantity)
            .map { listOfReports ->
                calculationOfAverageReportData(listOfReports, domainRequestReport.reportingPeriod)
            }
    }

    private fun saveInCache(domainSaveReportString: DomainSaveReportString): Completable {
        return repository.saveInCache(domainSaveReportString)
    }

    override fun openReportFromCache(): DomainSaveReportString {
        return repository.openReportFromCache()
    }

    private fun convertReportDataToReportString(
        cityName: String,
        reportingPeriod: ReportingPeriod,
        domainResponseReport: DomainResponseReport
    ): DomainSaveReportString {
        return DomainSaveReportString(
            reportString = "??????????: $cityName\n" +
                    "?????????????? ???????????????? ???? ???????????? \"${reportingPeriod.stringQuantity}\":\n" +
                    "?????????????????????? ${(domainResponseReport.temperature - 273.15).toStringDoubleFormat()} ??C\n" +
                    "?????????????????? ${domainResponseReport.humidity.toStringDoubleFormat()} %\n" +
                    "???????????????? ${domainResponseReport.pressure.toStringDoubleFormat()} ??????\n" +
                    "?????????? ${domainResponseReport.wind.toStringDoubleFormat()} ??/??\n" +
                    "???????????? ${domainResponseReport.precipitation.toStringDoubleFormat()} ????"
        )
    }

    private fun calculationOfAverageReportData(
        listOfReports: List<DomainResponseReport>,
        reportingPeriod: ReportingPeriod
    ): DomainResponseReport {
        val sumItemReportData = calculateSumHistoryData(listOfReports)

        return DomainResponseReport(
            temperature = sumItemReportData.temperature / reportingPeriod.quantity,
            pressure = sumItemReportData.pressure / reportingPeriod.quantity,
            humidity = sumItemReportData.humidity / reportingPeriod.quantity,
            wind = sumItemReportData.wind / reportingPeriod.quantity,
            precipitation = sumItemReportData.precipitation / reportingPeriod.quantity,
        )
    }

    private fun calculateSumHistoryData(list: List<DomainResponseReport>): DomainResponseReport {
        return Observable.fromIterable(list)
            .scan { accumulator: DomainResponseReport, itemDataHistory: DomainResponseReport ->
                DomainResponseReport(
                    temperature = accumulator.temperature + itemDataHistory.temperature,
                    pressure = accumulator.pressure + itemDataHistory.pressure,
                    humidity = accumulator.humidity + itemDataHistory.humidity,
                    wind = accumulator.wind + itemDataHistory.wind,
                    precipitation = accumulator.precipitation + itemDataHistory.precipitation
                )
            }.blockingLast()
    }

    private fun calculateDayStepDay(step: Int): Int {
        return SimpleDateFormat("dd", Locale("ru"))
            .format(System.currentTimeMillis() - 86400000 * step).toInt()
    }

    private fun calculateMonthStepDay(step: Int): Int {
        return SimpleDateFormat("MM", Locale("ru"))
            .format(System.currentTimeMillis() - 86400000 * step).toInt()
    }

    private fun calculateMonthStepMonth(step: Int): Int {
        return SimpleDateFormat("MM", Locale("ru"))
            .format(System.currentTimeMillis() - 2592000000 * step).toInt()
    }

    companion object {
        const val TAG = "ReportInt_Logging"
    }
}