package com.example.weatherapplication

import java.text.SimpleDateFormat
import java.util.*


fun <T : Date> T.format(format: String): String {
    return SimpleDateFormat(format).format(this)
}