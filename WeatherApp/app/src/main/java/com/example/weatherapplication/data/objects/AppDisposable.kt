package com.example.weatherapplication.data.objects

import io.reactivex.disposables.CompositeDisposable


class AppDisposable {
    val disposableList = CompositeDisposable()

    fun unsubscribeAll() {
        disposableList.clear()
    }
}