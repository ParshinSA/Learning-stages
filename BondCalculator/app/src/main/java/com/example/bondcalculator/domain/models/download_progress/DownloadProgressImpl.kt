package com.example.bondcalculator.domain.models.download_progress

import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadProgressImpl @Inject constructor() : DownloadProgress {

    private var progress: PublishSubject<ProgressData>? = null

    override fun start(): PublishSubject<ProgressData> {
        progress = PublishSubject.create()
        return progress!!
    }

    override fun setProgressData(progressData: ProgressData) {
        progress?.onNext(progressData)
    }

    override fun stop() {
        progress?.onComplete()
    }
}