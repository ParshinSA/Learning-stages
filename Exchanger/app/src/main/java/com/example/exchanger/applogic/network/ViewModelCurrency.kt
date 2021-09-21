package com.example.exchanger.applogic.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exchanger.applogic.RemoteCurrency
import com.example.exchanger.applogic.network.RepositoryCurrency

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





