package com.example.weatherapplication.common

import android.content.Context
import android.util.DisplayMetrics
import retrofit2.Retrofit
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

fun Number.toStringDoubleFormat(): String {
    this.toDouble()
    return String.format("%,.2f", this)
}