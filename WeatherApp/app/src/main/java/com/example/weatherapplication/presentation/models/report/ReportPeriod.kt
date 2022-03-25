package com.example.weatherapplication.presentation.models.report

enum class ReportPeriod(val stringQuantity: String, val quantity:Int) {
    TEN_DAYS("Десять дней",10),
    ONE_MONTH("Один месяц",1),
    THREE_MONTHS("Три месяца",3)
}