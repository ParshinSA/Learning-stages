package com.example.exchanger.deprecate

import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

//object NetWorkResponseCourse {
//
//    private val client = OkHttpClient.Builder().build()
//
//    fun requestCall(): Call {
//        val url = HttpUrl.Builder()
//            .scheme("https")
//            .host("www.cbr-xml-daily.ru")
//            .addPathSegment("daily_json.js")
//            .build()
//
//        val request = Request.Builder()
//            .get()
//            .url(url)
//            .build()
//
//        return client.newCall(request)
//    }
//}