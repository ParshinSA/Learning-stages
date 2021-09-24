package com.example.exchanger.deprecate


// https://www.cbr-xml-daily.ru/daily_json.js

//class RepositoryCurrency {
//    fun requestCourse(
//        currencyKey: List<String>,
//        callbackListCurse: (List<CurrencyRemote>) -> Unit
//    ) {
//        val thread = Thread {
//            try {
//                val response = NetWorkResponseDBCourse.requestCall().execute()
//                val responseString = response.body?.string()
//                val parseResponseString = parseResponse(responseString!!, currencyKey)
//                callbackListCurse(parseResponseString)
//            } catch (e: IOException) {
//                Log.d("MyServer", "Error response e:${e.message}")
//            }
//        }
//        thread.start()
//        thread.join()
//    }
//
//    private fun parseResponse(
//        responseString: String,
//        currencyKey: List<String>
//    ): List<CurrencyRemote> {
//        return try {
//            val jsonObject = JSONObject(responseString)
//            val ojectValuteList = jsonObject.getJSONObject("Valute")
//
//
//            val listCurrency = mutableListOf<CurrencyRemote>()
//            currencyKey.forEach { key ->
//                if (key != "RUB") {
//                    val currentCurrency = ojectValuteList.getJSONObject(key)
//                    listCurrency.add(
//                        CurrencyRemote(
//                            type = key,
//                            course = currentCurrency.getDouble("Value")
//                        )
//                    )
//                } else {
//                    listCurrency.add(
//                        CurrencyRemote(
//                            type = "RUB",
//                            course = 1.0
//                        )
//                    )
//                }
//            }
//            Log.d("MyServer", "listCurrency:${listCurrency}")
//            listCurrency
//        } catch (e: Exception) {
//            Log.d("MyServer", "Error parse e:${e.message}")
//            emptyList()
//        }
//    }
