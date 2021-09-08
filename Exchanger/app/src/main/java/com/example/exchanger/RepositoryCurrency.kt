package com.example.exchanger

import android.util.Log
import org.json.JSONObject
import java.io.IOException
import java.lang.Exception

class RepositoryCurrency {
    fun requestDataBaseCourse(
        callbackDataBase: (List<Currency22>) -> Unit
    ) {
        Thread {
            try {
                val response = NetWorkResponseDBCourse.requestCall().execute()
                val responseString = response.body?.string()
                Log.d("ServerServer", "request = $responseString")
                val parseResponseStr = parseResponse(responseString!!)
                callbackDataBase(parseResponseStr)
            } catch (e: IOException) {
                Log.d("ServerServer", "request = null")
            }
        }.start()
    }

    private fun parseResponse(responseString: String): List<Currency22> {
        return try {
            val jsonObject = JSONObject(responseString)
            val currencyArray = jsonObject.getJSONArray("Value")
            val dbCurrency = (0 until currencyArray.length()).map { index ->
                currencyArray.getJSONObject(index)
            }.map { currencyJSONObject ->
                Currency22(
                    name = currencyJSONObject.getString("CharCode"),
                    courseToRub = currencyJSONObject.getDouble("Value")
                )
            }
            Log.d("ServerServer", "list = $dbCurrency")
            dbCurrency
        } catch (e: Exception) {
            Log.d("ServerServer", "request = null")
            emptyList()
        }
    }

}