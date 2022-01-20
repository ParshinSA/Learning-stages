package com.example.weatherapplication.ui

import android.app.Application
import android.util.Log
import com.example.weatherapplication.data.db.appdb.AppDatabaseInit
import com.example.weatherapplication.data.db.appsp.SharedPrefs
import com.example.weatherapplication.services.NotificationChannels

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
        init()
    }

    private fun init() {
        initDatabase()
        initSharedPrefs()
        initNotificationChannel()
    }

    private fun initNotificationChannel() {
        NotificationChannels.create(this)
    }

    private fun initSharedPrefs() {
        SharedPrefs.initSharedPref(this)
    }

    private fun initDatabase() {
        AppDatabaseInit.initDatabase(this)
    }

    companion object {
        const val TAG = "Application_Logging"
    }
}