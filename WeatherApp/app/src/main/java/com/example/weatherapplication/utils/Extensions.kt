package com.example.weatherapplication.utils

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
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

fun Int.fromDpToPixels(context: Context): Int {
    val density = context.resources.displayMetrics.densityDpi
    val pixelsInDp = density / DisplayMetrics.DENSITY_DEFAULT
    return this * pixelsInDp
}