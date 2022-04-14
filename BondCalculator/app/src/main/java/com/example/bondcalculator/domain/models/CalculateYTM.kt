package com.example.bondcalculator.domain.models

import com.example.bondcalculator.common.toTimeStampSecond
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

class CalculateYTM(
    private val currentSecurity: DomainSecuritiesData
) {
}


fun main(){
    val a = System.currentTimeMillis().convertToDate("yyyy-MM-dd").toTimeStampSecond()
    val b = "2022-04-14".toTimeStampSecond()

    println("${(b - a)/86400}")
}


fun Long.convertToDate(format: String): String {
    val sdf = SimpleDateFormat(format)
    val netDate = Date(this )
    return sdf.format(netDate)
}