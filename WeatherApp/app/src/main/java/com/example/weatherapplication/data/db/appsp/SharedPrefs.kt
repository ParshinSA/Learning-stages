package com.example.weatherapplication.data.db.appsp

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.weatherapplication.ui.weather.shortforecastlist.ShortForecastListFragment

object SharedPrefs {

    const val TAG = "SharedPrefs_Logging"
    lateinit var instancePrefs: SharedPreferences

    fun initSharedPref(context: Context) {
        instancePrefs =
            context.getSharedPreferences(
                SharedPrefsContract.SHARED_PREFS_NAME,
                Context.MODE_PRIVATE
            )
    }
}