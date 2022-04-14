package com.example.bondcalculator.domain.models

data class DomainSecuritiesData(
    val secId: String,
    val boardId: String, // id биржы
    val shortName: String,
    val couponValue: Double, // размер купонной выплаты
    val nextCoupon: Long, // дата следуещей купонной выплаты
    val couponPeriod: Int, // периодичность купонных выплат
    val prevWaPrice: Double

)
