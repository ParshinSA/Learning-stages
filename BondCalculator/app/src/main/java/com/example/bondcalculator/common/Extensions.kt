package com.example.bondcalculator.common

import androidx.fragment.app.Fragment
import com.example.bondcalculator.presentation.AppApplication
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

fun String.toTimeStampSeconds(format: String = DEFAULT_DATE_FORMAT): Long {
    return SimpleDateFormat(format).parse(this).time / 1000
}

fun Long.toDateString(format: String = DEFAULT_DATE_FORMAT): String {
    val sdf = SimpleDateFormat(format)
    val netDate = Date(this * 1000)
    return sdf.format(netDate)
}

fun Long.toDayTimeStamp(): Long {
    return this.toSeconds().toDateString().toTimeStampSeconds()
}

fun Double.roundDouble(): Double {
    return round(this * ONE_HUNDRED_PERCENT) / ONE_HUNDRED_PERCENT
}

fun Long.toSeconds(): Long {
    return this / 1000
}