package com.example.weatherapplication.domain

enum class ReportingPeriod(val stringQuantity: String, val quantity:Int) {
    TEN_DAYS("Десять дней",10),
    ONE_MONTH("Один месяц",1),
    THREE_MONTHS("Три месяца",3)
}