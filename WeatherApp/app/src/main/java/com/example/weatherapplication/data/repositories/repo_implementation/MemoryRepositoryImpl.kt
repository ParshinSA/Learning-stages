package com.example.weatherapplication.data.repositories.repo_implementation

import android.content.Context
import android.util.Log
import com.example.weatherapplication.data.models.report.DataHistory
import com.example.weatherapplication.data.repositories.repo_interface.MemoryRepository
import com.example.weatherapplication.ui.common.ReportPeriods
import com.example.weatherapplication.common.toStringDoubleFormat
import io.reactivex.Completable
import io.reactivex.Observable
import java.io.File


class MemoryRepositoryImpl(
    private val contextObs: Observable<Context>
) : MemoryRepository {

    var context: Context? = null

    init {
        Log.d("OBser2", "provideMemoryRepositoryImpl: ObservContext")
        contextObs.subscribe {
            context = it
        }
    }

    override fun saveReportInCacheDirection(
        nameCity: String,
        period: ReportPeriods,
        report: DataHistory
    ): Completable {
        return Completable.create { subscriber ->
            val file = File(context?.cacheDir, "report.txt")
            try {
                file.outputStream().buffered().use {
                    it.write(
                        ("Город: $nameCity\n" +
                                "Средние значения за период \"${period.stringQuantity}\":\n" +
                                "температура ${(report.temperature.medianValue - 273.15).toStringDoubleFormat()} °C\n" +
                                "влажность ${report.humidity.medianValue.toStringDoubleFormat()} %\n" +
                                "давление ${report.pressure.medianValue.toStringDoubleFormat()} гПа\n" +
                                "ветер ${report.wind.medianValue.toStringDoubleFormat()} м/с\n" +
                                "осадки ${report.precipitation.medianValue.toStringDoubleFormat()} мм")
                            .toByteArray()
                    )
                }
                subscriber.onComplete()
            } catch (t: Throwable) {
                subscriber.onError(t)
            }
        }
    }

    override fun openReportFromCacheDir(): String {
        val folder = context?.cacheDir
        val file = File(folder, "report.txt")
        file.inputStream().bufferedReader().use {
            return it.readText()
        }
    }
}