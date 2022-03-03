package com.example.weatherapplication.ui

import android.app.Application
import android.util.Log
import com.example.weatherapplication.data.db.app_sp.SharedPrefs
import com.example.weatherapplication.data.objects.CustomCities
import com.example.weatherapplication.di.AppComponent
import com.example.weatherapplication.di.DaggerAppComponent
import com.example.weatherapplication.di.modules.AppModule
import com.example.weatherapplication.services.NotificationChannels

class AppApplication : Application() {

     val appComponent: AppComponent by lazy {
         DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()
     }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")

        init()
    }

    private fun init() {
        initSharedPrefs()
        initNotificationChannel()
    }

    private fun initNotificationChannel() {
        NotificationChannels.create(this)
    }

    private fun initSharedPrefs() {
        SharedPrefs.initSharedPref(this)
    }

    companion object {
        const val TAG = "Application_Logging"
    }
}