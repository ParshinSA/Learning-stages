package com.example.weatherapplication.data.db.app_sp

import android.content.Context
import android.content.SharedPreferences

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