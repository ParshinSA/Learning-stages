package com.example.weatherapplication.domain.interactors

import com.example.weatherapplication.domain.interactors.interactors_interface.ReportInteractor
import com.example.weatherapplication.domain.models.report.DomainReportDto
import com.example.weatherapplication.domain.models.report.request.DomainRequestReportDto
import com.example.weatherapplication.domain.models.report.response.DomainResponseReportDto
import com.example.weatherapplication.domain.repository.ReportRepository
import com.example.weatherapplication.presentation.common.toStringDoubleFormat
import com.example.weatherapplication.presentation.models.report.ReportPeriod
import com.example.weatherapplication.presentation.models.report.request.UiRequestReportDto
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ReportInteractorImpl @Inject constructor(
    private val repository: ReportRepository
) : ReportInteractor {

    override fun generateReport(uiRequestReportDto: UiRequestReportDto): Completable {
        return requestRemoteReport(uiRequestReportDto)
            .map { domainResponseReportDto ->
                convertReportDataToReportString(
                    cityName = uiRequestReportDto.cityName,
                    reportPeriod = uiRequestReportDto.reportPeriod,
                    domainResponseReportDto = domainResponseReportDto
                )
            }
            .flatMapCompletable { reportString ->
                saveInCache(reportString)
            }
    }

    private fun requestRemoteReport(
        uiRequestReportDto: UiRequestReportDto
    ): Observable<DomainResponseReportDto> {
        return Observable.fromIterable(0 until uiRequestReportDto.reportPeriod.quantity)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMap { stepTime ->

                if (uiRequestReportDto.reportPeriod.stringQuantity == ReportPeriod.TEN_DAYS.stringQuantity) {

                    val day = calculateDayStepDay(stepTime)
                    val month = calculateMonthStepDay(stepTime)

                    repository.requestReportToDay(
                        DomainRequestReportDto(
                            latitude = uiRequestReportDto.latitude,
                            longitude = uiRequestReportDto.longitude,
                            day = day,
                            month = month
                        )
                    )

                } else {
                    val month = calculateMonthStepMonth(stepTime)

                    repository.requestReportToMonth(
                        DomainRequestReportDto(
                            latitude = uiRequestReportDto.latitude,
                            longitude = uiRequestReportDto.longitude,
                            month = month
                        )
                    )
                }
            }
            .buffer(uiRequestReportDto.reportPeriod.quantity)
            .map { listOfReports ->
                calculationOfAverageReportData(listOfReports, uiRequestReportDto.reportPeriod)
            }
    }

    private fun saveInCache(domainReportDto: DomainReportDto): Completable {
        return repository.saveInCache(domainReportDto)
    }

    override fun openReportFromCache(): DomainReportDto {
        return repository.openReportFromCache()
    }

    private fun convertReportDataToReportString(
        cityName: String,
        reportPeriod: ReportPeriod,
        domainResponseReportDto: DomainResponseReportDto
    ): DomainReportDto {
        return DomainReportDto(
            reportString = "Город: $cityName\n" +
                    "Средние значения за период \"${reportPeriod.stringQuantity}\":\n" +
                    "температура ${(domainResponseReportDto.temperature - 273.15).toStringDoubleFormat()} °C\n" +
                    "влажность ${domainResponseReportDto.humidity.toStringDoubleFormat()} %\n" +
                    "давление ${domainResponseReportDto.pressure.toStringDoubleFormat()} гПа\n" +
                    "ветер ${domainResponseReportDto.wind.toStringDoubleFormat()} м/с\n" +
                    "осадки ${domainResponseReportDto.precipitation.toStringDoubleFormat()} мм"
        )
    }

    private fun calculationOfAverageReportData(
        listOfReports: List<DomainResponseReportDto>,
        reportPeriod: ReportPeriod
    ): DomainResponseReportDto {
        val sumItemReportData = calculateSumHistoryData(listOfReports)

        return DomainResponseReportDto(
            temperature = sumItemReportData.temperature / reportPeriod.quantity,
            pressure = sumItemReportData.pressure / reportPeriod.quantity,
            humidity = sumItemReportData.humidity / reportPeriod.quantity,
            wind = sumItemReportData.wind / reportPeriod.quantity,
            precipitation = sumItemReportData.precipitation / reportPeriod.quantity,
        )
    }

    private fun calculateSumHistoryData(list: List<DomainResponseReportDto>): DomainResponseReportDto {
        return Observable.fromIterable(list)
            .scan { accumulator: DomainResponseReportDto, itemDataHistory: DomainResponseReportDto ->
                DomainResponseReportDto(
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