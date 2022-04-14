package com.example.bondcalculator.data.networking.models

import com.example.bondcalculator.common.toTimeStampSecond
import com.example.bondcalculator.domain.models.DomainRequestSecuritiesData
import com.example.bondcalculator.domain.models.DomainSecuritiesData

fun DomainRequestSecuritiesData.toRemoteRequestSecuritiesData(): RemoteRequestSecuritiesData {
    return RemoteRequestSecuritiesData(
        boardId = boardId
    )
}

fun RemoteResponseSecuritiesDataDto.toDomainSecuritiesData(): List<DomainSecuritiesData> {
    val domainSecuritiesDataList = mutableListOf<DomainSecuritiesData>()
    for (dataList in this.data.listParameterData) {

        fun getParameter(parameterName: String): Any? {
            this.data.listParameterName.indexOf(parameterName)
                .let { index ->
                    if (index < 0) error("Данный параметр не найден $parameterName")
                    else return dataList[index]
                }
        }

        val domain = DomainSecuritiesData(
            secId = getParameter("SECID")?.toString() ?: DEFAULT_STRING,

            boardId = getParameter("BOARDID")?.toString() ?: DEFAULT_STRING,

            shortName = getParameter("SHORTNAME")?.toString() ?: DEFAULT_STRING,

            couponValue =
            getParameter("COUPONVALUE")?.toString()?.toDouble() ?: DEFAULT_DOUBLE,

            couponPeriod =
            getParameter("COUPONPERIOD")?.toString()?.toDouble()?.toInt() ?: DEFAULT_INT,

            nextCoupon =
            getParameter("NEXTCOUPON")?.toString()?.toTimeStampSecond() ?: DEFAULT_TIME_STAMP,

            prevWaPrice =
            getParameter("PREVWAPRICE")?.toString()?.toDouble() ?: DEFAULT_DOUBLE
        )

        domainSecuritiesDataList.add(domain)
    }

    return domainSecuritiesDataList
}

const val DEFAULT_DOUBLE = 0.0
const val DEFAULT_STRING = ""
const val DEFAULT_INT = 0
const val DEFAULT_TIME_STAMP = 0L
