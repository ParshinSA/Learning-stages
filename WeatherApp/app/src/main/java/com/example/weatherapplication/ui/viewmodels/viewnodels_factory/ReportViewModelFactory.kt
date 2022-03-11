package com.example.weatherapplication.ui.viewmodels.viewnodels_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapplication.data.repositories.repo_interface.MemoryRepository
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import com.example.weatherapplication.ui.viewmodels.viewmodels.ReportViewModel

class ReportViewModelFactory(
    private val remoteRepo: RemoteRepository,
    private val memoryRepo: MemoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReportViewModel(
            remoteRepo = remoteRepo,
            memoryRepo = memoryRepo
        ) as T
    }
}