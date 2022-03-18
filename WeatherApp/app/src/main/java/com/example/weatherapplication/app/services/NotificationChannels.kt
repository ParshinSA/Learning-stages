package com.example.weatherapplication.app.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

object NotificationChannels {
    const val UPDATE_SERVICE_CHANNEL_ID = "UPDATE_SERVICE_CHANNEL_ID"

    fun create(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createUpdateServiceChannelId(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createUpdateServiceChannelId(context: Context) {
        val name = "Update Forecast"
        val priority = NotificationManager.IMPORTANCE_HIGH

        val channel = NotificationChannel(UPDATE_SERVICE_CHANNEL_ID, name, priority)
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }
}