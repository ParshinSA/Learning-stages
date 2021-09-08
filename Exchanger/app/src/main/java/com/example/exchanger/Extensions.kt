package com.example.exchanger

import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import java.lang.Math.min

fun EditText.mySetTextColor(@ColorRes color: Int) {
    this.setTextColor(ContextCompat.getColor(context, color))
}

fun EditText.mySetSelection(currentSelectionPos: Int, userValue: String) {
    fun calculatePlusPosSelection(): Int {
        val userValueNumber = userValue.deleteRank().toDoubleOrNull()?:0.0
        var counter = 0
        userValue.forEach { if (it == ' ') counter++ }

        return when (userValueNumber) {
            in 0.0..999.0 -> 0
            in 1000.0..999999.0 -> if (counter == 1) 0 else 1
            in 1000000.0..999999999.0 -> if (counter == 1) 0 else 1
            in 1000000000.0..999999999999.0 -> if (counter == 1) 0 else 1
            else -> 0
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
    return this.replace(" ", "")
        .replace(",", ".")
}


//
//fun Number.delimiter(): String {
//    var leftNum: String = ""
//    var rightNum: String = ""
//    var delimiter: Char? = null
//    var indexDelimiter: Int = 0
//    var count = 0
//
//    val thisToString = this.toString()
//
//    thisToString.forEachIndexed { index: Int, c: Char ->
//        if (c == '.' || c == '.') {
//            indexDelimiter = index
//            delimiter = c
//        }
//    }
//
//    return if (delimiter != null) {
//        thisToString.forEachIndexed { index, c ->
//            if (index < thisToString.length) {
//                if (index < indexDelimiter) leftNum += c
//                if (index > indexDelimiter) rightNum += c
//            }
//        }
//        leftNum = leftNum.reversed()
//
//        var temporarilyLeft = ""
//        leftNum.forEach {
//            if (count != 3) {
//                temporarilyLeft += it
//                count++
//            } else {
//                temporarilyLeft += " "
//                count = 0
//            }
//        }
//
//        count = 0
//        var temporarilyRight = ""
//        rightNum.forEach {
//            if (count != 2) {
//                temporarilyRight += it
//                count++
//            }
//        }
//
//        temporarilyLeft.reversed() + delimiter + temporarilyRight
//    } else {
//        var temporarilyThis = ""
//        thisToString.reversed().forEach {
//            if (count != 3) {
//                temporarilyThis += it
//                count++
//            } else {
//                temporarilyThis += " "
//                count = 0
//            }
//        }
//        temporarilyThis.reversed()
//    }
//
//}
