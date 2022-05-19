package com.example.bondcalculator.domain.models.download_progress

import io.reactivex.subjects.BehaviorSubject

interface DownloadProgress {

    fun setProgressData(progressData: ProgressData)
    fun getProgressData(): BehaviorSubject<ProgressData>
}
