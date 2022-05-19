package com.example.bondcalculator.domain.models.download_progress

import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadProgressImpl @Inject constructor() : DownloadProgress {

    private val progress = BehaviorSubject.create<ProgressData>()

    override fun setProgressData(progressData: ProgressData) {
        progress.onNext(progressData)
    }

    override fun getProgressData(): BehaviorSubject<ProgressData> {
        return progress
    }
}