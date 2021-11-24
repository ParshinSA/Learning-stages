package com.example.weatherapplication.utils

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.weatherapplication.R
import com.example.weatherapplication.ui.weather.detailsforecast.DetailsForecastFragment
import com.example.weatherapplication.ui.weather.shortforecastlist.ShortForecastListFragment
import com.google.gson.GsonBuilder
import java.text.SimpleDateFormat
import java.util.*


fun <T : Long> T.convertToDate(format: String): String {
    val sdf = SimpleDateFormat(format)
    val netDate = Date(this * 1000L)
    return sdf.format(netDate)
}

fun <T : Fragment> T.logD(message: String) {
    Log.d("SystemLogging", "${this.javaClass.simpleName} -> $message")
}

