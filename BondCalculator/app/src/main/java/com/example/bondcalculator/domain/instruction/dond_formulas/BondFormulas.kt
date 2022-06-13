package com.example.bondcalculator.domain.instruction.dond_formulas

import com.example.bondcalculator.domain.models.bonds_data.DomainBondAndCalendar

interface BondFormulas {
    fun getTotalPrice(bond: DomainBondAndCalendar, currentDate: Long): Double
    fun getMaturityPrice(bond: DomainBondAndCalendar, currentDate: Long): Double
    fun getPriceToMaturity(bond: DomainBondAndCalendar, currentDate: Long): Double
    fun getCouponPay(bond: DomainBondAndCalendar, currentDate: Long): Double
    fun getAmortPay(bond: DomainBondAndCalendar, currentDate: Long): Double
    fun getAccumulateCoupon(bond: DomainBondAndCalendar, currentDate: Long): Double
}