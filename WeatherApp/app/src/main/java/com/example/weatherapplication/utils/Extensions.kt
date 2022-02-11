package com.example.weatherapplication.utils

import android.content.Context
import android.util.DisplayMetrics
import java.text.SimpleDateFormat
import java.util.*


fun <T : Long> T.convertToDate(format: String): String {
    val sdf = SimpleDateFormat(format)
    val netDate = Date(this * 1000L)
    return sdf.format(netDate)
}

fun Int.fromDpToPixels(context: Context): Int {
    val density = context.resources.displayMetrics.densityDpi
    val pixelsInDp = density / DisplayMetrics.DENSITY_DEFAULT
    return this * pixelsInDp
}

fun calculateDayStepDay(step: Int): Int {
    return SimpleDateFormat("dd")
        .format(System.currentTimeMillis() - 86400000 * step).toInt()
}

fun calculateMonthStepDay(step: Int): Int {
    return SimpleDateFormat("MM")
        .format(System.currentTimeMillis() - 86400000 * step).toInt()
}

fun calculateMonthStepMonth(step: Int): Int {
    return SimpleDateFormat("MM")
        .format(System.currentTimeMillis() - 2592000000 * step).toInt()
}
