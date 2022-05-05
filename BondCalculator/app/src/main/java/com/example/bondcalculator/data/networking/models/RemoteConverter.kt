package com.example.bondcalculator.data.networking.models

import com.example.bondcalculator.common.toTimeStampSeconds
import com.example.bondcalculator.data.networking.models.bonds_data.RemoteRequestBondsList
import com.example.bondcalculator.data.networking.models.bonds_data.RemoteResponseBondListDto
import com.example.bondcalculator.data.networking.models.exchange_rate.RemoteResponseExchangeRateDto
import com.example.bondcalculator.data.networking.models.payment_calendar.RemoteResponseCouponInfoDto
import com.example.bondcalculator.data.networking.models.payment_calendar.nested_response.RemoteRequestCouponInfo
import com.example.bondcalculator.domain.models.bonds_data.DomainBond
import com.example.bondcalculator.domain.models.bonds_data.DomainRequestBondList
import com.example.bondcalculator.domain.models.exchange_rate.DomainExchangeRateUsdToRub
import com.example.bondcalculator.domain.models.payment_calendar.DomainPaymentCalendar
import com.example.bondcalculator.domain.models.payment_calendar.DomainRequestPaymentCalendar

fun RemoteResponseExchangeRateDto.toDomainExchangeRateUsdToRub(): DomainExchangeRateUsdToRub {
    return DomainExchangeRateUsdToRub(
        value = valuteData.currentValute.value
    )
}

fun DomainRequestBondList.toRemoteRequestBondData(): RemoteRequestBondsList {
    return RemoteRequestBondsList(
        boardId = boardId
    )
}

fun DomainRequestPaymentCalendar.toRemoteRequestCouponInfo(): RemoteRequestCouponInfo {
    return RemoteRequestCouponInfo(
        securityId = securityId
    )
}

fun RemoteResponseBondListDto.toDomainBondData(): List<DomainBond> {
    val domainSecuritiesDataList = mutableListOf<DomainBond>()

    for (dataList in this.data.listParameterData) {

        with(this.data) {

            val domain = DomainBond(
                secId = getParameter(listParameterName, dataList, "SECID")
                    ?.toString()
                    ?: "",
                shortName = getParameter(listParameterName, dataList, "SHORTNAME")
                    ?.toString()
                    ?: "",
                yieldPercent = getParameter(listParameterName, dataList, "YIELDATPREVWAPRICE")
                    ?.toString()
                    ?.toDouble()
                    ?: 0.0,
                couponPercent = getParameter(listParameterName, dataList, "COUPONPERCENT")
                    ?.toString()
                    ?.toDouble()
                    ?: 0.0,
                nextDateCouponPayment = getParameter(listParameterName, dataList, "NEXTCOUPON")
                    ?.toString()
                    ?.toTimeStampSeconds()
                    ?: 0,
                accumulatedCoupon = getParameter(listParameterName, dataList, "ACCRUEDINT")
                    ?.toString()
                    ?.toDouble()
                    ?: 0.0,
                pricePercent = getParameter(listParameterName, dataList, "PREVPRICE")
                    ?.toString()
                    ?.toDouble()
                    ?: 0.0,
                lotSize = getParameter(listParameterName, dataList, "LOTSIZE")
                    ?.toString()
                    ?.toDouble()
                    ?.toInt()
                    ?: 0,
                nominal = getParameter(listParameterName, dataList, "LOTVALUE")
                    ?.toString()
                    ?.toDouble()
                    ?: 0.0,
                repayment = getParameter(listParameterName, dataList, "MATDATE")
                    ?.toString()
                    ?.toTimeStampSeconds()
                    ?: 0,
                couponPeriod = getParameter(listParameterName, dataList, "COUPONPERIOD")
                    ?.toString()
                    ?.toDouble()
                    ?.toInt()
                    ?: 0,
            )

            if (domain.pricePercent > 0.0) domainSecuritiesDataList.add(domain)
        }
    }

    return domainSecuritiesDataList
}

fun RemoteResponseCouponInfoDto.toDomainCouponInfo(): DomainPaymentCalendar {
    val amortizationPayment = hashMapOf<Long, Double>()
    val couponPayment = hashMapOf<Long, Double>()

    with(this.amortizationInfo) {
        for (dataList in listParameterData) {

            val couponDatePayment = getParameter(listParameterName, dataList, "amortdate")
                ?.toString()
                ?.toTimeStampSeconds()
                ?: error("$this amortdate = null")

            val couponValuePayment = getParameter(listParameterName, dataList, "value")
                ?.toString()
                ?.toDouble()
                ?: error("$this value = null")

            amortizationPayment[couponDatePayment] = couponValuePayment
        }
    }

    with(this.couponInfo) {
        for (dataList in listParameterData) {

            val couponDatePayment = getParameter(listParameterName, dataList, "coupondate")
                ?.toString()
                ?.toTimeStampSeconds()
                ?: error("couponPayment = null")

            val couponValuePayment = getParameter(listParameterName, dataList, "value")
                ?.toString()
                ?.toDouble()
                ?: 0.0

            couponPayment[couponDatePayment] = couponValuePayment
        }
    }

    return DomainPaymentCalendar(
        amortizationPayment = amortizationPayment,
        couponPayment = couponPayment
    )
}

fun getParameter(parameterList: List<String>, dataList: List<Any?>, parameterName: String): Any? {
    parameterList.indexOf(parameterName)
        .let { index ->
            if (index < 0) error("Данный параметр не найден $parameterName")
            else return dataList[index]
        }
}