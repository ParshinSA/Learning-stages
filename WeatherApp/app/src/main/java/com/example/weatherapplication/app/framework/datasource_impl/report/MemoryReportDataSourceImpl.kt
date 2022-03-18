package com.example.weatherapplication.app.framework.datasource_impl.report

import android.content.Context
import com.example.weatherapplication.app.common.contracts.ReportContract
import com.example.weatherapplication.data.data_source.report.MemoryReportDataSource
import io.reactivex.Completable
import java.io.File

class MemoryReportDataSourceImpl(
    private val context: Context
) : MemoryReportDataSource {

    override fun saveInCache(report: String): Completable {
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

    override fun openFromCache(): String { // todo обращение к памяти, переделать на фоновый поток
        val folder = context.cacheDir
        val file = File(folder, ReportContract.NAME_FILE)

        file.inputStream()
            .bufferedReader()
            .use {
                return it.readText()
            }
    }


}