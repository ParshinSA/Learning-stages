package com.example.weatherapplication.data.datetime

enum class DateTimeFormat(val format: String) {
    TIME("HH:mm:ss"),
    DATA("dd.MM.yyyy"),
    DATA_TIME("dd.MM.yyyy. HH:mm:ss"),
    TIME_DATA("HH:mm:ss. dd.MM.yyyy")
}