package com.example.bondcalculator.domain.instruction.dond_formulas

import com.example.bondcalculator.common.ONE_DAY_SECONDS
import com.example.bondcalculator.common.ONE_HUNDRED_PERCENT
import com.example.bondcalculator.common.roundDouble
import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar
import javax.inject.Inject

class BondFormulasImpl @Inject constructor() : BondFormulas {

    // накопленный купон
    override fun getAccumulateCoupon(bond: DomainBondAndCalendar, currentDate: Long): Double {
        val dateLastCouponPayment =
            bond.paymentCalendar.couponPayment.keys.lastOrNull() { it <= currentDate } ?: return 0.0
        val nextCoupon = getNextCoupon(bond, currentDate)
        // количество дней с последней даты выплаты купона
        val numberDay = (currentDate - dateLastCouponPayment) / ONE_DAY_SECONDS
        // НКД в день
        val chargesForOneDay = (nextCoupon / bond.couponPeriod)
        // накопленный купонный доход
        return (numberDay * chargesForOneDay).roundDouble()
    }

    // стоимость при покупке
    override fun getTotalPrice(bond: DomainBondAndCalendar, currentDate: Long): Double {
        return ((getMarketPrice(bond, currentDate) + getAccumulateCoupon(bond, currentDate)))
            .roundDouble()
    }

    // стоимость при погашении
    override fun getMaturityPrice(bond: DomainBondAndCalendar, currentDate: Long): Double {
        val currentNominal = getNominal(bond, currentDate)
        val currentCoupon = getCouponPay(bond, currentDate)
        return ((currentNominal + currentCoupon)).roundDouble()
    }

    // стоимость при продаже до погашения
    override fun getPriceToMaturity(bond: DomainBondAndCalendar, currentDate: Long): Double {
        return (maxOf(getMarketPrice(bond, currentDate), getNominal(bond, currentDate))
                + getAccumulateCoupon(bond, currentDate)).roundDouble()
    }

    // текущая рыночная стоимость
    private fun getMarketPrice(bond: DomainBondAndCalendar, currentDate: Long): Double {
        return ((getNominal(bond, currentDate) / ONE_HUNDRED_PERCENT) * bond.pricePercent)
            .roundDouble()
    }

    // следующий купон
    private fun getNextCoupon(bond: DomainBondAndCalendar, currentDate: Long): Double {
        val keyDate = bond.paymentCalendar.couponPayment.keys.firstOrNull() { it >= currentDate }
        return if (keyDate == null) 0.0
        else bond.paymentCalendar.couponPayment[keyDate]!!
    }

    // текущий номинал
    private fun getNominal(bond: DomainBondAndCalendar, currentDate: Long): Double {
        var currentNominal = 0.0
        bond.paymentCalendar.amortizationPayment.forEach { (datePayment, valuePayment) ->
            if (datePayment >= currentDate) currentNominal += valuePayment
        }
        return currentNominal.roundDouble()
    }

    override fun getCouponPay(bond: DomainBondAndCalendar, currentDate: Long): Double {
        val keyDate = bond.paymentCalendar.couponPayment.keys.firstOrNull() { it == currentDate }
        return if (keyDate == null) 0.0
        else bond.paymentCalendar.couponPayment[keyDate]!!
    }

    override fun getAmortPay(bond: DomainBondAndCalendar, currentDate: Long): Double {
        val keyDate =
            bond.paymentCalendar.amortizationPayment.keys.firstOrNull() { it == currentDate }
        return if (keyDate == null) 0.0
        else bond.paymentCalendar.amortizationPayment[keyDate]!!
    }
}