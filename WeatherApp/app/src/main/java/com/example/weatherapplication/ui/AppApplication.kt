package com.example.weatherapplication.ui

import android.app.Application
import android.util.Log
import com.example.weatherapplication.data.db.forecastdb.ForecastDbInit
import com.example.weatherapplication.data.db.appsp.SharedPrefs
import com.example.weatherapplication.data.db.customcitiesdb.CustomCitiesDbInit
import com.example.weatherapplication.data.objects.CustomCities
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
        observeDatabase()
    }

    private fun initNotificationChannel() {
        NotificationChannels.create(this)
    }

    private fun initSharedPrefs() {
        SharedPrefs.initSharedPref(this)
    }

    private fun initDatabase() {
        ForecastDbInit.initDatabase(this)
        CustomCitiesDbInit.initDatabase(this)
    }

    private fun observeDatabase(){
        CustomCities.observeCustomCitiesDb()
    }

    companion object {
        const val TAG = "Application_Logging"
    }
}