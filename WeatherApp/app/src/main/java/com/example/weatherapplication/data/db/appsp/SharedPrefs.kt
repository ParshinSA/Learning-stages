package com.example.weatherapplication.data.db.appsp

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.weatherapplication.ui.weather.shortforecastlist.ShortForecastListFragment

object SharedPrefs {
    const val TAG = "SharedPrefs_Logging"
    lateinit var instancePrefs: SharedPreferences
    private lateinit var changeListener: SharedPreferences.OnSharedPreferenceChangeListener

    fun initSharedPref(context: Context) {
        instancePrefs =
            context.getSharedPreferences(
                SharedPrefsContract.SHARED_PREFS_NAME,
                Context.MODE_PRIVATE
            )
    }

    fun addChangeListener() {
        Log.d(TAG, "addChangeListener: ")
        changeListener =  SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            SharedPrefsListener.isSharedPrefs(true)
            Log.d(ShortForecastListFragment.TAG, "observeSharedPrefs: change $key")
        }
        instancePrefs.registerOnSharedPreferenceChangeListener(changeListener)
    }

    fun removeListener(){
        if (this::changeListener.isInitialized)
        instancePrefs.unregisterOnSharedPreferenceChangeListener(changeListener)
    }


}