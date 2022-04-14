package com.example.bondcalculator.common

import java.text.SimpleDateFormat

fun String.toTimeStampSecond(): Long {
    return SimpleDateFormat("yyyy-MM-dd").parse(this).time/1000
}