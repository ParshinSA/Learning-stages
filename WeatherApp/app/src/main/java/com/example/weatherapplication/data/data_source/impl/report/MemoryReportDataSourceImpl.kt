package com.example.weatherapplication.data.data_source.impl.report

import android.content.Context
import com.example.weatherapplication.data.data_source.interf.report.MemoryReportDataSource
import com.example.weatherapplication.data.memory.ReportContract
import io.reactivex.Completable
import java.io.File
import javax.inject.Inject

class MemoryReportDataSourceImpl @Inject constructor(
    private val context: Context
) : MemoryReportDataSource {

    override fun openReportFromCache(): String {
        val folder = context.cacheDir
        val file = File(folder, ReportContract.NAME_FILE)
        file.inputStream().bufferedReader().use {
            return it.readText()
        }
    }

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

}