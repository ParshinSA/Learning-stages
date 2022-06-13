package com.example.bondcalculator.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.bondcalculator.presentation.common.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()

    private val errorMessageSingleLiveEvent = SingleLiveEvent<String>()
    val errorMessageLiveData: LiveData<String> get() = errorMessageSingleLiveEvent

    fun errorMessage(message: String) {
        errorMessageSingleLiveEvent.postValue(message)
    }
}