package com.example.weatherapplication.ui.viewmodels

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(
) : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()
}