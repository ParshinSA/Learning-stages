package com.example.weatherapplication.ui

import android.app.Application
import com.example.weatherapplication.data.db.appdb.AppDatabase
import com.example.weatherapplication.data.db.appsp.AppSharedPreferences

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        AppDatabase.initDatabase(this)
        AppSharedPreferences.initSharedPref(this)
    }
}