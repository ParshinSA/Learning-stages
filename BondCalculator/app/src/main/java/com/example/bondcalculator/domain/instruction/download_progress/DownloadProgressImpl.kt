package com.example.bondcalculator.domain.instruction.download_progress

import com.example.bondcalculator.domain.models.download_progress.DomainDownloadProgressData
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadProgressImpl @Inject constructor() : DownloadProgress {

    private var progress: PublishSubject<DomainDownloadProgressData>? = null

    override fun start(): PublishSubject<DomainDownloadProgressData> {
        progress = PublishSubject.create()
        return progress!!
    }

    override fun setProgressData(progressData: DomainDownloadProgressData) {
        progress?.onNext(progressData)
    }

    override fun stop() {
        progress?.onComplete()
    }
}