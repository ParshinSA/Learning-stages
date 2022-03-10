package com.example.weatherapplication.common

import com.google.gson.GsonBuilder

/**
 * @author Parshin Sergey
 * Объект описывающий конвертацию объектов типа <T> в строку Json и обратно
 * @see objectToString конвертация объекта в строку Json
 * @see stringToObject конвертация строки Json в объект
 * */

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
