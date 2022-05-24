package com.example.bondcalculator.domain.models.download_progress

import io.reactivex.subjects.PublishSubject

interface DownloadProgress {

    fun setProgressData(progressData: ProgressData)
    fun start(): PublishSubject<ProgressData>
    fun stop()
}
