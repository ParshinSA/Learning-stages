package com.example.weatherapplication.services

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.weatherapplication.common.AppState
import com.example.weatherapplication.data.common.SharedPrefsContract
import com.example.weatherapplication.data.repositories.repo_interface.CustomCitiesDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.ForecastDbRepository
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import com.example.weatherapplication.ui.AppApplication
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class UpdateForecastService : Service() {

    private lateinit var forecastDbRepo: ForecastDbRepository
    private lateinit var remoteRepo: RemoteRepository
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var appState: AppState
    private lateinit var customCitiesDbRepo: CustomCitiesDbRepository

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

    @Inject
     fun injectDependency(
        forecastDbRepo: ForecastDbRepository,
        remoteRepo: RemoteRepository,
        compositeDisposable: CompositeDisposable,
        sharedPreferences: SharedPreferences,
        appState: AppState,
        customCitiesDbRepo: CustomCitiesDbRepository
    ) {
        this.forecastDbRepo = forecastDbRepo
        this.remoteRepo = remoteRepo
        this.compositeDisposable = compositeDisposable
        this.sharedPreferences = sharedPreferences
        this.appState = appState
        this.customCitiesDbRepo = customCitiesDbRepo
    }

    private fun updateForecast() {

        if (checkSDK() && appState.isCollapsed) {
            Log.d(TAG, "updateForecast: startForeground")
            startForeground(FOREGROUND_ID, createNotification())
        } else Log.d(TAG, "updateForecast: startStartedService")

        update()

        if (checkSDK() && appState.isCollapsed) {
            stopForeground(true)
            Log.d(TAG, "updateForecast: stopForeground")
        }

        stopSelf()
        Log.d(TAG, "updateForecast: stopSelf")
    }

    private fun update() {
        compositeDisposable.add(
            customCitiesDbRepo.getCityList()
                .subscribeOn(Schedulers.io())
                .subscribe({ cityList ->
                    compositeDisposable.add(
                        remoteRepo.requestForecastAllCity(cityList).subscribe({ forecast ->
                            Log.d(TAG, "response update -> save db: $forecast")
                            forecastDbRepo.saveForecastInDatabase(forecast)
                        }, {
                            Log.d(TAG, "updateForecast: ERROR $it")
                        }, {
                            saveTimeUpdateForecast()
                            compositeDisposable.clear()
                        })
                    )
                }, {
                    Log.d(TAG, "update: no city in database $it")
                })
        )
    }

    private fun saveTimeUpdateForecast() {
        val currentTime = System.currentTimeMillis()
        sharedPreferences.edit()
            .putLong(SharedPrefsContract.TIME_LAST_REQUEST_KEY, currentTime)
            .apply()
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