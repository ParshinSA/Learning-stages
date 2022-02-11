package com.example.weatherapplication.data.repositories

import android.content.Context
import com.example.weatherapplication.data.models.report.HistoryData
import io.reactivex.Completable
import java.io.File


class MemoryRepository(
    private val context: Context
) {

    fun saveReportInCacheDirection(
        nameCity: String,
        period: String,
        report: HistoryData
    ): Completable {
        return Completable.create { subscriber ->
            val file = File(context.cacheDir, "report.txt")
            try {
                file.outputStream().buffered().use {
                    it.write(
                        ("Город: $nameCity\n" +
                                "Средние значения за период \"${period}\":\n" +
                                "температура ${(report.temp.median - 273.15).toInt()} °C\n" +
                                "влажность ${report.humidity.median} %\n" +
                                "давление ${report.pressure.median} гПа\n" +
                                "ветер ${report.wind.median.toInt()} м/с\n" +
                                "осадки ${report.precipitation.median} мм").toByteArray()
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