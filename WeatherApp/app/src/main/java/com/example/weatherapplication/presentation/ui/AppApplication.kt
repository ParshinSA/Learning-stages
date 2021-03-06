package com.example.weatherapplication.presentation.ui

import android.app.Application
import android.util.Log
import com.example.weatherapplication.di.AppComponent
import com.example.weatherapplication.di.DaggerAppComponent
import com.example.weatherapplication.services.NotificationChannels

class AppApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .context(context = this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
        init()
    }

    private fun init() {
        initNotificationChannel()
    }

    private fun initNotificationChannel() {
        NotificationChannels.create(this)
    }

    companion object {
        const val TAG = "Application_Logging"
    }
}