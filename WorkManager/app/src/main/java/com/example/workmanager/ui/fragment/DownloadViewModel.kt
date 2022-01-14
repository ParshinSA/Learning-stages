package com.example.workmanager.ui.fragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.workmanager.data.repositories.DownloadRepository
import com.example.workmanager.data.workers.DownloadWorkerContract
import kotlinx.coroutines.launch

class DownloadViewModel(
    application: Application
) : AndroidViewModel(application) {
    init {
        Log.d("SystemLogging", "DownloadViewModel")
    }

    private val downloadRepository = DownloadRepository(application)

    fun downloadFile(url: String) {
        viewModelScope.launch {
            try {
                downloadRepository.downloadFile(url)
            } catch (t: Throwable) {
                Log.d("SystemLogging", "ERROR DownloadViewModel/downloadFile t:$t")
            }
        }
    }

    fun stopDownload() {
        downloadRepository.stopDownload()
    }

}