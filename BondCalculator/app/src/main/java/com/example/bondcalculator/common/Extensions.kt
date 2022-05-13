package com.example.bondcalculator.common

import android.util.Log
import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar
import com.example.bondcalculator.domain.models.payment_calendar.DomainPaymentCalendar
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

fun DomainBondAndCalendar.checkCalendar(currentDate: Long): DomainBondAndCalendar {

    return if (currentDate + ONE_YEAR_SECONDS < this.repayment) {
        Log.d("TAG portfolio", "checkCalendar: return this")
        this
    } else {
        Log.d("TAG portfolio", "checkCalendar: return new bond")
        val newAmortizationPaymentCalendar = TreeMap<Long, Double>()
        val stepAmortizationPayment: Long =
            currentDate - this.paymentCalendar.amortizationPayment.keys.toList().minOrNull()!!

        for ((date, value) in this.paymentCalendar.amortizationPayment) {
            newAmortizationPaymentCalendar[(date + stepAmortizationPayment)] = value
        }

        val newCouponPaymentCalendar = TreeMap<Long, Double>()
        val stepCouponPayment: Long =
            currentDate - this.paymentCalendar.couponPayment.keys.toList().sorted()[0]

        for ((date, value) in this.paymentCalendar.couponPayment) {
            newCouponPaymentCalendar[(date + stepCouponPayment)] = value
        }

        val repayment = newAmortizationPaymentCalendar.keys.toList().maxOrNull()!!

        DomainBondAndCalendar(
            secId = secId,
            shortName = shortName,
            couponValuePercent = couponValuePercent,
            pricePercent = pricePercent,
            lotSize = lotSize,
            repayment = repayment,
            couponPeriod = couponPeriod,
            paymentCalendar = DomainPaymentCalendar(
                amortizationPayment = newAmortizationPaymentCalendar,
                couponPayment = newCouponPaymentCalendar
            )
        )
    }
}
