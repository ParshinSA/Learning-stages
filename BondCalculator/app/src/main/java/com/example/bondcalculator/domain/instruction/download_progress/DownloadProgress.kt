package com.example.bondcalculator.domain.instruction.download_progress

import com.example.bondcalculator.domain.models.download_progress.DomainDownloadProgressData
import io.reactivex.subjects.PublishSubject

interface DownloadProgress {

    fun setProgressData(progressData: DomainDownloadProgressData)
    fun start(): PublishSubject<DomainDownloadProgressData>
    fun stop()
}
