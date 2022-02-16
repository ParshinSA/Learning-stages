package com.example.weatherapplication.data.repositories

import android.content.Context
import com.example.weatherapplication.data.models.report.HistoryData
import com.example.weatherapplication.ui.weather.weather_report.Period
import com.example.weatherapplication.utils.toStringDoubleFormat
import io.reactivex.Completable
import java.io.File


class MemoryRepository(
    private val context: Context
) {

    fun saveReportInCacheDirection(
        nameCity: String,
        period: Period,
        report: HistoryData
    ): Completable {
        return Completable.create { subscriber ->
            val file = File(context.cacheDir, "report.txt")
            try {
                file.outputStream().buffered().use {
                    it.write(
                        ("Город: $nameCity\n" +
                                "Средние значения за период \"${period.stringQuantity}\":\n" +
                                "температура ${(report.temp.median - 273.15).toStringDoubleFormat()} °C\n" +
                                "влажность ${report.humidity.median.toStringDoubleFormat()} %\n" +
                                "давление ${report.pressure.median.toStringDoubleFormat()} гПа\n" +
                                "ветер ${report.wind.median.toStringDoubleFormat()} м/с\n" +
                                "осадки ${report.precipitation.median.toStringDoubleFormat()} мм")
                            .toByteArray()
                    )
                }
                subscriber.onComplete()
            } catch (t: Throwable) {
                subscriber.onError(t)
            }
        }
    }

    fun openReportFromCacheDir(): String {
        val folder = context.cacheDir
        val file = File(folder, "report.txt")
        file.inputStream().bufferedReader().use {
            return it.readText()
        }
    }
}