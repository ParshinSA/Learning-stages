package com.example.weatherapplication.data.datetime

import java.util.*

object DateTime {
    private val calendar: Calendar
        get() = Calendar.getInstance()

    fun getTime(): Date {
        return calendar.time
    }
}