package com.example.exchanger

import org.json.JSONObject
import java.io.IOException
import java.lang.Exception
// https://www.cbr-xml-daily.ru/daily_json.js

class RepositoryCurrency {
    fun requestDataBaseCourse(
        callbackDataBase: (List<RemoteCurrency>) -> Unit
    ) {
        Thread {
            try {
                val response = NetWorkResponseDBCourse.requestCall().execute()
                val responseString = response.body?.string()
                val parseResponseStr = parseResponse(responseString!!)
                callbackDataBase(parseResponseStr)
            } catch (e: IOException) {
            }
        }.start()
    }

    private fun parseResponse(responseString: String): List<RemoteCurrency> {
        return try {
            val jsonObject = JSONObject(responseString)
            val ojectValute = jsonObject.getJSONObject("Valute")
            val keysInValute = ojectValute.keys()

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
            emptyList()
        }
    }

}