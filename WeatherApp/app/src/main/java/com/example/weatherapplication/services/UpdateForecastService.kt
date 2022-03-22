package com.example.weatherapplication.services

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.weatherapplication.domain.interactors.interactors_interface.ForecastInteractor
import com.example.weatherapplication.presentation.common.AppState
import com.example.weatherapplication.presentation.ui.AppApplication
import javax.inject.Inject

class UpdateForecastService : Service() {

    @Inject
    lateinit var interactor: ForecastInteractor

    @Inject
    lateinit var appState: AppState

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        inject()
        Log.d(TAG, "onCreate: ")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")
        updateForecast()
        return START_STICKY
    }

    private fun inject() {
        (this.applicationContext as AppApplication).appComponent.inject(this)
    }

    private fun updateForecast() {

        if (checkSDK() && appState.isCollapsed) {
            Log.d(TAG, "updateForecast: startForeground")
            startForeground(FOREGROUND_ID, createNotification())
        } else Log.d(TAG, "updateForecast: startStartedService")

        interactor.updateForecast().subscribe({

        },{
            Log.d(TAG, "updateForecast: error $it")
        })

        if (checkSDK() && appState.isCollapsed) {
            stopForeground(true)
            Log.d(TAG, "updateForecast: stopForeground")
        }

        stopSelf()
        Log.d(TAG, "updateForecast: stopSelf")
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, NotificationChannels.UPDATE_SERVICE_CHANNEL_ID)
            .setContentTitle("Update forecast")
            .build()
    }

    private fun checkSDK() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
    }

    companion object {
        const val FOREGROUND_ID = 123
        const val TAG = "UpdateService_Logging"
    }
}