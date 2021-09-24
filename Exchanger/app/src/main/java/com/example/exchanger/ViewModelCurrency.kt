package com.example.exchanger


import android.util.Log
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exchanger.CalculatingValues
import com.example.exchanger.RepositoryCurrency
import com.example.exchanger.currency.AllValute

class ViewModelCurrency : ViewModel() {

    private val course = MutableLiveData<Double>(0.0)

    private val dataBuyLiveData = MutableLiveData<Double>()
    val dataBuy: LiveData<Double>
        get() = dataBuyLiveData

    private val dataSellLiveData = MutableLiveData<Double>()
    val dataSell: LiveData<Double>
        get() = dataSellLiveData

    private val incorrectValueSellLiveData = MutableLiveData<Boolean>()
    val incorrectValueSell: LiveData<Boolean>
        get() = incorrectValueSellLiveData

    fun calculateExchange(
        field: EditText,
        userValue: Double
    ) {
        if (field.id == R.id.edit_sell
            && userValue < course.value!!
        ) incorrectValueSellLiveData.postValue(true)
        else {
            incorrectValueSellLiveData.postValue(false)
            CalculatingValues().initCalculate(
                field = field,
                userValue = userValue,
                course = course.value!!,
                callbackBuyValues = { dataBuyLiveData.postValue(it) },
                callbackSellValues = { dataSellLiveData.postValue(it) }
            )
        }
    }

    fun getCourse(
        typeCurrencyBuy: String,
        typeCurrencySell: String,
    ) {
        RepositoryCurrency().requestCourse { listCurrencyCourse ->
            val courseBuyToBuy = getValue(typeCurrencyBuy, listCurrencyCourse)
            val courseBuyToSell = getValue(typeCurrencySell, listCurrencyCourse)
            course.postValue(courseBuyToBuy / courseBuyToSell)
        }
    }

    private fun getValue(typeCurrency: String, listCurrencyCourse: AllValute): Double {
        return when (typeCurrency) {
            "RUB" -> 1.0
            "USD" -> listCurrencyCourse.usd.course
            "EUR" -> listCurrencyCourse.eur.course
            "GBP" -> listCurrencyCourse.gbp.course
            else -> {
                Log.d("MyServer", "Incorrect type currency $typeCurrency")
                0.0
            }
        }
    }

}