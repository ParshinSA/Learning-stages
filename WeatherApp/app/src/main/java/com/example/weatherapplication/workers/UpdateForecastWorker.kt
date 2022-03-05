package com.example.weatherapplication.workers

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.weatherapplication.data.objects.AppState
import com.example.weatherapplication.services.UpdateForecastService
import com.example.weatherapplication.ui.AppApplication
import javax.inject.Inject

class UpdateForecastWorker(
    private val context: Context,
    params: WorkerParameters
) : Worker(
    context,
    params
) {

    @Inject
    lateinit var appState: AppState

    override fun doWork(): Result {
        (this.applicationContext as AppApplication).appComponent.inject(this)
        return try {
            Log.d(TAG, "doWork: try")
            startUpdateService()
            Result.success()
        } catch (t: Throwable) {
            Log.d(TAG, "doWork: catch")
            Result.failure()
        }
    }

    private fun startUpdateService() {
        try {
            val intentUpdateService = Intent(context, UpdateForecastService::class.java)

            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                context.startService(intentUpdateService)
                return
            }

            if (appState.isCollapsed) {
                Log.d(TAG, "startUpdateService: startForegroundService")
                context.startForegroundService(intentUpdateService)
            } else {
                Log.d(TAG, "startUpdateService: startService")
                context.startService(intentUpdateService)
            }

        } catch (t: Throwable) {
            Log.d(TAG, "startUpdateService: t:$t")
        }
    }

    companion object {
        const val UPDATE_FORECAST_WORKER_NAME = "UPDATE_FORECAST_WORKER_ID"
        const val TAG = "UpdateWorker_Logging"
    }
}