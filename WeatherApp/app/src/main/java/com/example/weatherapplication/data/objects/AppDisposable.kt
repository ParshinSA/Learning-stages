package com.example.weatherapplication.data.objects

import io.reactivex.disposables.CompositeDisposable


object AppDisposable {
    val disposableList = CompositeDisposable()

    fun unSubscribeAll(){
        disposableList.clear()
    }
}