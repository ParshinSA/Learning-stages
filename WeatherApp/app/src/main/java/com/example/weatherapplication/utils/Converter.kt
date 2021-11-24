package com.example.weatherapplication.utils

import com.google.gson.GsonBuilder

object Converter {

    inline fun <reified T> objectToString(mObject: T): String {
        val adapter = GsonBuilder().create().getAdapter(T::class.java)
        return adapter.toJson(mObject)
    }

    inline fun <reified T> stringToObject(mString: String): T {
        val adapter = GsonBuilder().create().getAdapter(T::class.java)
        return adapter.fromJson(mString)
    }
}

