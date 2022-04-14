package com.example.bondcalculator

import java.util.*


/**
 * Доходность к погашению
 * */
fun yieldToMaturity() {


}

/**
 * НАКОПЛЕННЫЙ КУПОННЫЙ ДОХОД (НКД)
 * https://iss.moex.com/iss/engines/stock/markets/bonds/boards/TQOB/securities // RUS
 * https://iss.moex.com/iss/engines/stock/markets/bonds/boards/TQOD/securities // евробонды в USD
 *
 * @param nextCoupone - дата следуещей купонной выплаты
 * @param couponePeriod - периодичность купонных выплат
 * @param couponeValue - размер купонной выплаты
 * */

//fun accumulateCouponIncome(securities: Securities): Double {
fun accumulateCouponIncome(
    couponValue: Double = 26.43,
    couponPeriod: Int = 182,
    nextCoupon: Long = 1664928000
): Double {
    val currentDate = System.currentTimeMillis()

    return couponValue * ((couponPeriod - (nextCoupon - currentDate) / 86400) / couponPeriod)
}

//fun main() {
//    val date = "2022-07-27"
//    val form = SimpleDateFormat("yyyy-MM-dd")
//    val docDate = form.parse(date).time/1000
//
//    println(docDate)
//}


