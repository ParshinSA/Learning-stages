package com.example.weatherapplication.ui

import android.app.Application
import com.example.weatherapplication.BuildConfig
import com.example.weatherapplication.data.db.appdb.AppDatabaseInit
import com.example.weatherapplication.data.db.appsp.AppSharedPreferences
import timber.log.Timber

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        initDatabase()
        initSharedPrefs()
        initTimber()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun initSharedPrefs() {
        AppSharedPreferences.initSharedPref(this)
    }

    private fun initDatabase() {
        AppDatabaseInit.initDatabase(this)
    }
}