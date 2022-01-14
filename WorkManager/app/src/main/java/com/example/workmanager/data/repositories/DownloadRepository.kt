package com.example.workmanager.data.repositories

import android.content.Context
import androidx.work.*
import com.example.workmanager.data.workers.DownloadWorker
import com.example.workmanager.data.workers.DownloadWorkerContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DownloadRepository(
    private val context: Context
) {

    suspend fun downloadFile(url: String) {
        withContext(Dispatchers.IO) {
            val workRequest = settingsWorker(url)
            startDownload(workRequest)
        }
    }

    private fun startDownload(workRequest: OneTimeWorkRequest) {
        WorkManager.getInstance(context)
            .enqueueUniqueWork(DownloadWorkerContract.ID, ExistingWorkPolicy.REPLACE, workRequest)
    }

    fun stopDownload() {
        WorkManager.getInstance(context)
            .cancelUniqueWork(DownloadWorkerContract.ID)
    }

    private fun settingsWorker(url: String): OneTimeWorkRequest {
        val workData = workDataOf(
            DownloadWorkerContract.URL_KEY to url
        )

        val workConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        return OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(workConstraints)
            .setInputData(workData)
            .build()
    }

}