package com.example.weatherapplication.domain.interactors

import android.content.Context
import com.example.weatherapplication.data.database.models.report.FieldValue
import com.example.weatherapplication.data.database.models.report.ReportData
import com.example.weatherapplication.data.database.models.report.WeatherStatistic
import com.example.weatherapplication.data.reporitories.ReportRepository
import com.example.weatherapplication.domain.ReportingPeriod
import com.example.weatherapplication.domain.interactors.interactors_interface.ReportInteractor
import com.example.weatherapplication.presentation.common.contracts.ReportContract
import com.example.weatherapplication.presentation.common.toStringDoubleFormat
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ReportInteractorImpl @Inject constructor(
    private val reportRepository: ReportRepository,
    private val context: Context,
    private val compositeDisposable: CompositeDisposable
) : ReportInteractor {

    override fun generateReport(
        cityName: String,
        latitude: Double,
        longitude: Double,
        reportingPeriod: ReportingPeriod
    ): Completable {
        return requestReportData(
            latitude = latitude,
            longitude = longitude,
            reportingPeriod = reportingPeriod
        )
            .map { reportData ->
                convertReportDataToReportString(
                    cityName = cityName,
                    reportingPeriod = reportingPeriod,
                    reportData = reportData
                )
            }
            .flatMapCompletable { reportString ->
                saveInCache(reportString)
            }
    }

    override fun openReportFromCache(): String {
        val folder = context.cacheDir
        val file = File(folder, ReportContract.NAME_FILE)
        file.inputStream().bufferedReader().use {
            return it.readText()
        }
    }

    private fun convertReportDataToReportString(
        cityName: String,
        reportingPeriod: ReportingPeriod,
        reportData: ReportData
    ): String {
        return "Город: $cityName\n" +
                "Средние значения за период \"${reportingPeriod.stringQuantity}\":\n" +
                "температура ${(reportData.temperature.medianValue - 273.15).toStringDoubleFormat()} °C\n" +
                "влажность ${reportData.humidity.medianValue.toStringDoubleFormat()} %\n" +
                "давление ${reportData.pressure.medianValue.toStringDoubleFormat()} гПа\n" +
                "ветер ${reportData.wind.medianValue.toStringDoubleFormat()} м/с\n" +
                "осадки ${reportData.precipitation.medianValue.toStringDoubleFormat()} мм"
    }

    private fun requestReportData(
        latitude: Double,
        longitude: Double,
        reportingPeriod: ReportingPeriod
    ): Observable<ReportData> {
        return Observable.fromIterable(0 until reportingPeriod.quantity)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMap { step ->

                if (reportingPeriod.stringQuantity == ReportingPeriod.TEN_DAYS.stringQuantity) {

                    val day = calculateDayStepDay(step)
                    val month = calculateMonthStepDay(step)

                    reportRepository.requestReportToDay(
                        latitude = latitude,
                        longitude = longitude,
                        day = day,
                        month = month
                    )

                } else {
                    val month = calculateMonthStepMonth(step)

                    reportRepository.requestReportToMonth(
                        latitude = latitude,
                        longitude = longitude,
                        month = month
                    )
                }
            }
            .map { statistical: WeatherStatistic ->
                statistical.reportData
            }
            .buffer(reportingPeriod.quantity)
            .map { listOfReports ->
                calculationOfAverageReportData(listOfReports, reportingPeriod)
            }
    }

    private fun saveInCache(
        report: String
    ): Completable {
        return Completable.create { subscriber ->
            val file = File(context.cacheDir, ReportContract.NAME_FILE)
            try {
                file.outputStream().buffered().use {
                    it.write(report.toByteArray())
                }
                subscriber.onComplete()
            } catch (t: Throwable) {
                subscriber.onError(t)
            }
        }
    }

    private fun calculationOfAverageReportData(
        listOfReports: List<ReportData>,
        reportingPeriod: ReportingPeriod
    ): ReportData {
        val sumItemReportData = calculateSumHistoryData(listOfReports)

        return ReportData(
            temperature = FieldValue(sumItemReportData.temperature.medianValue / reportingPeriod.quantity),
            pressure = FieldValue(sumItemReportData.pressure.medianValue / reportingPeriod.quantity),
            humidity = FieldValue(sumItemReportData.humidity.medianValue / reportingPeriod.quantity),
            wind = FieldValue(sumItemReportData.wind.medianValue / reportingPeriod.quantity),
            precipitation = FieldValue(sumItemReportData.precipitation.medianValue / reportingPeriod.quantity),
        )
    }

    private fun calculateSumHistoryData(list: List<ReportData>): ReportData {
        return Observable.fromIterable(list)
            .scan { accumulator: ReportData, itemDataHistory: ReportData ->
                ReportData(
                    temperature = FieldValue(accumulator.temperature.medianValue + itemDataHistory.temperature.medianValue),
                    pressure = FieldValue(accumulator.pressure.medianValue + itemDataHistory.pressure.medianValue),
                    humidity = FieldValue(accumulator.humidity.medianValue + itemDataHistory.humidity.medianValue),
                    wind = FieldValue(accumulator.wind.medianValue + itemDataHistory.wind.medianValue),
                    precipitation = FieldValue(accumulator.precipitation.medianValue + itemDataHistory.precipitation.medianValue)
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