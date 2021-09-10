package com.example.exchanger

import android.util.Log
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

// https://www.cbr-xml-daily.ru/daily_json.js

class RepositoryCurrency {
    fun requestDataBaseCourse(
        callbackListCurse: (List<RemoteCurrency>) -> Unit
    ) {
        Thread {
            try {
                val response = NetWorkResponseDBCourse.requestCall().execute()
                val responseString = response.body?.string()
                val parseResponseString = parseResponse(responseString!!)
                callbackListCurse(parseResponseString)
            } catch (e: IOException) {
                emptyList<RemoteCurrency>()
                Log.d("MyServer", "Error response e:${e.message}")
            }
        }.start()
    }

    private fun parseResponse(responseString: String): List<RemoteCurrency> {
        return try {
            val jsonObject = JSONObject(responseString)
            val ojectValute = jsonObject.getJSONObject("Valute")
//            val keysInValute = ojectValute.keys()  // получние всех ключей внутри "Valute"
            val keysInValute = listOf<String>("USD", "GBP", "EUR")


            val listCurrency = mutableListOf<RemoteCurrency>()
            keysInValute.forEach { key ->
                val currentCurrency = ojectValute.getJSONObject(key)
                listCurrency.add(
                    RemoteCurrency(
                        type = key,
                        course = currentCurrency.getDouble("Value")
                    )
                )
            }
            listCurrency
        } catch (e: Exception) {
            Log.d("MyServer", "Error parse e:${e.message}")
            emptyList()
        }
    }

}