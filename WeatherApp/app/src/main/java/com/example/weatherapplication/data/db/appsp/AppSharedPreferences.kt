package com.example.weatherapplication.data.db.appsp

import android.content.Context
import android.content.SharedPreferences

object AppSharedPreferences {
    lateinit var instancePrefs: SharedPreferences

    fun initSharedPref(context: Context) {
        instancePrefs =
            context.getSharedPreferences(AppSharedPreferencesContract.FILE_NAME, Context.MODE_PRIVATE)
    }
}