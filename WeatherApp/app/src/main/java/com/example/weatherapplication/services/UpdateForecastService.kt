package com.example.weatherapplication.services

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.weatherapplication.data.db.app_sp.SharedPrefs
import com.example.weatherapplication.data.db.app_sp.SharedPrefsContract
import com.example.weatherapplication.data.objects.AppDisposable
import com.example.weatherapplication.data.objects.AppState
import com.example.weatherapplication.data.repositories.RemoteRepository
import io.reactivex.disposables.CompositeDisposable

class UpdateForecastService : Service() {
    private val remoteRepo = RemoteRepository(this)

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        Log.d(TAG, "onCreate: ")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")
        updateForecast()
        return START_STICKY
    }

    private fun updateForecast() {

        if (checkSDK() && AppState.isCollapsedAppLiveData.value == true) {
            Log.d(TAG, "updateForecast: startForeground")
            startForeground(FOREGROUND_ID, createNotification())
        } else Log.d(TAG, "updateForecast: startService")

        AppDisposable.disposableList.add(
            remoteRepo.requestForecastAllCity().subscribe({ forecast ->
                remoteRepo.saveForecastInDatabase(forecast)
            }, {
                Log.d(TAG, "updateForecast: ERROR $it")
            },{
                saveTimeUpdateForecast()
            })
        )

        if (checkSDK() && AppState.isCollapsedAppLiveData.value == true) {
            stopForeground(true)
            Log.d(TAG, "updateForecast: stopForeground")
        }

        stopSelf()
        Log.d(TAG, "updateForecast: stopSelf")
    }

    private fun saveTimeUpdateForecast() {

        val currentTime = System.currentTimeMillis()

        SharedPrefs.instancePrefs.edit()
            .putLong(SharedPrefsContract.TIME_LAST_REQUEST_KEY, currentTime)
            .apply()

        Log.d(TAG, "saveTimeUpdateForecast: time:$currentTime")
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