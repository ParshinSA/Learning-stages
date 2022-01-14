package com.example.workmanager.data.workers

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.workmanager.data.networking.Networking
import java.io.File
import java.io.IOException

class DownloadWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Log.d("SystemLogging", "DownloadWorker/doWork")

        return try {
            downloadFile()
            Log.d("SystemLogging", "SUCCESS DownloadWorker")
            Result.success()
        } catch (t: Throwable) {
            Log.d("SystemLogging", "ERROR DownloadWorker")
            Result.retry()
        }
    }

    private suspend fun downloadFile() {
        Log.d("SystemLogging", "DownloadWorker/downloadFile")
        val urlToDownload = inputData.getString(DownloadWorkerContract.URL_KEY)
            ?: error("ERROR inputData.getString")

        val nameFile = getNameFile(urlToDownload)
        val myFolder = createFolder()
        val myFile = File(myFolder, nameFile)

        try {
            myFile.outputStream()
                .use { outputStream ->
                    Networking.googleApi.download(urlToDownload)
                        .byteStream()
                        .use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    Log.d("SystemLogging", "DownloadWorker/downloadFile/try")
                }

        } catch (e: IOException) {
            myFile.delete()
            Log.d("SystemLogging", "DownloadWorker/downloadFile/catch $e")
        }
    }

    private fun getNameFile(url: String): String =
        url.filterIndexed { index, _ ->
            index > url.lastIndexOf('/')
        }

    private fun createFolder(): File? =
        if (checkStorageState()) {
            context.getExternalFilesDir("MyDownload")
        } else {
            null
        }

    private fun checkStorageState(): Boolean =
        Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

}