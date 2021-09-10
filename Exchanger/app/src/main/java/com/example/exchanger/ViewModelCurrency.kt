package com.example.exchanger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelCurrency : ViewModel() {
    private val repositoryCurrency = RepositoryCurrency()

    private val listCurrencyCurseLiveData = MutableLiveData<List<RemoteCurrency>>()
    val listCurrencyCurse: LiveData<List<RemoteCurrency>>
        get() = listCurrencyCurseLiveData

    fun getCourses() {
        repositoryCurrency.requestDataBaseCourse { dataBaseCurrency ->
            listCurrencyCurseLiveData.postValue(dataBaseCurrency)
        }
    }
}





