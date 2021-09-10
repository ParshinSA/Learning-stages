package com.example.exchanger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelCurrency : ViewModel() {
    private val repository = RepositoryCurrency()

    private val dataBaseCurrencyLiveData = MutableLiveData<List<RemoteCurrency>>()
    val dataBaseCurrency: LiveData<List<RemoteCurrency>>
        get() = dataBaseCurrencyLiveData

    fun getCourses() {
        repository.requestDataBaseCourse { dataBaseCurrency ->
            dataBaseCurrencyLiveData.postValue(dataBaseCurrency)
        }
    }
}





