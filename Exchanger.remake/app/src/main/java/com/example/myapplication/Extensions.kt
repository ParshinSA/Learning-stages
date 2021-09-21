package com.example.myapplication

import android.util.Log
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import java.lang.Math.min
import java.math.BigDecimal

fun EditText.mySetTextColor(@ColorRes color: Int) {
    this.setTextColor(ContextCompat.getColor(context, color))
}

fun EditText.mySetSelection(currentSelectionPos: Int, userValue: String) {
    fun calculatePlusPosSelection(): Int {
        val userValueNumber = userValue.deleteRank().toDoubleOrNull() ?: 0.0
        var counter = 0
        userValue.forEach { if (it == ' ') counter++ }

        return when (userValueNumber) {
            in 0.0..999.0 ->
                when (counter) {
                    0 -> 0
                    1 -> -1
                    else -> Log.d("Server", "incorrect counter = $counter")
                }
            in 1000.0..999999.0 ->
                when (counter) {
                    0 -> 1
                    1 -> 0
                    2 -> -1
                    else -> Log.d("Server", "incorrect counter = $counter")
                }
            in 1000000.0..999999999.0 ->
                when (counter) {
                    1 -> 1
                    2 -> 0
                    3 -> -1
                    else -> Log.d("Server", "incorrect counter = $counter")
                }
            in 1000000000.0..999999999999.0 ->
                when (counter) {
                    2 -> 1
                    3 -> 0
                    4 -> -1
                    else -> Log.d("Server", "incorrect counter = $counter")
                }
            else -> -1
        }
    }

    this.setSelection(
        min((currentSelectionPos + calculatePlusPosSelection()), this.text.length)
    )
}


fun Number.myToStringRankInt(): String {
    this.toDouble()
    return String.format("%,.0f", this)
}

fun Number.myToStringRankDouble(): String {
    this.toDouble()
    return String.format("%,.2f", this)
}


fun String.deleteRank(): String {
    return this
        .replace(" ", "")
        .replace(",", ".")
//        .replace(" ", "")
}

//
//fun Number.toDecimalInt(): String {
//    this.toString().deleteRank()
//
//    var leftPart = ""
//    var delimiter = ""
//    var transformThis = ""
//
//    this.toString().toBigDecimal().toPlainString().forEach {
//        when {
//            it == '.' -> delimiter = it.toString()
//            delimiter == "" -> leftPart += it
//        }
//    }
//
//    var step = 3
//    leftPart.reversed().forEach { char ->
//        if (step > 0) {
//            transformThis += char
//            step--
//        } else {
//            transformThis += " $char"
//            step = 3
//        }
//    }
//
//    return transformThis.reversed()
//}
//
//fun Number.toDecimalDouble(): String {
//    this.toString().deleteRank()
//
//    var leftPart = ""
//    var rightPart = ""
//    var delimiter = ""
//    var transformThis = ""
//
//    this.toString().forEach {
//        when {
//            it == '.' -> delimiter = it.toString()
//            delimiter == "" -> leftPart += it
//            else -> rightPart += it
//        }
//    }
//
//    var step = 3
//    leftPart.reversed().forEachIndexed { _, char ->
//        if (step > 0) {
//            transformThis += char
//            step--
//        } else {
//            transformThis += " $char"
//            step = 2
//        }
//    }
//
//    transformThis = transformThis.reversed()
//
//    if (delimiter != "") transformThis += delimiter
//
//    if (rightPart.toInt() > 0) {
//        rightPart.forEachIndexed { index, char ->
//            if (index <= 1) transformThis += char
//        }
//    }
//
//    return transformThis
//}
