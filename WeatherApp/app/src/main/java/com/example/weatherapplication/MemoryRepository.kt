package com.example.weatherapplication//package com.example.weatherapplication.repositories
//
//import android.content.Context
//import com.example.weatherapplication.app.common.toStringDoubleFormat
//import com.example.weatherapplication.data.db.models.report.DataHistory
//import com.example.weatherapplication.data.data_source.MemoryDataSource
//import com.example.weatherapplication.domain.ReportPeriods
//import io.reactivex.Completable
//import java.io.File
//import javax.inject.Inject
//
//
//class MemoryRepository @Inject constructor(
//    private val context: Context
//) : MemoryDataSource {
//
//    override fun saveReportInCacheDirection(
//        nameCity: String,
//        period: ReportPeriods,
//        report: DataHistory
//    ): Completable {
//        return Completable.create { subscriber ->
//            val file = File(context.cacheDir, "report.txt")
//            try {
//                file.outputStream().buffered().use {
//                    it.write(
//                        ("Город: $nameCity\n" +
//                                "Средние значения за период \"${period.stringQuantity}\":\n" +
//                                "температура ${(report.temperature.medianValue - 273.15).toStringDoubleFormat()} °C\n" +
//                                "влажность ${report.humidity.medianValue.toStringDoubleFormat()} %\n" +
//                                "давление ${report.pressure.medianValue.toStringDoubleFormat()} гПа\n" +
//                                "ветер ${report.wind.medianValue.toStringDoubleFormat()} м/с\n" +
//                                "осадки ${report.precipitation.medianValue.toStringDoubleFormat()} мм")
//                            .toByteArray()
//                    )
//                }
//                subscriber.onComplete()
//            } catch (t: Throwable) {
//                subscriber.onError(t)
//            }
//        }
//    }
//
//    override fun openReportFromCacheDir(): String {
//        val folder = context.cacheDir
//        val file = File(folder, "report.txt")
//        file.inputStream().bufferedReader().use {
//            return it.readText()
//        }
//    }
//}