package com.example.weatherapplication.utils

import android.content.Context
import android.util.DisplayMetrics
import com.example.weatherapplication.ui.AppApplication
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
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

fun Retrofit.Builder.addAdapterAndConverterFactory(): Retrofit.Builder {
    return this.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
}

fun Number.toStringDoubleFormat(): String {
    this.toDouble()
    return String.format("%,.2f", this)
}