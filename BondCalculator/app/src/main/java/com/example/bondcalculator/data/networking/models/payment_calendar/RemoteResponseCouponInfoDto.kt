package com.example.bondcalculator.data.networking.models.payment_calendar

import com.example.bondcalculator.data.networking.models.payment_calendar.nested_response.Info
import com.google.gson.annotations.SerializedName

data class RemoteResponseCouponInfoDto(

    @SerializedName("amortizations")
    val amortizationInfo: Info,

    @SerializedName("coupons")
    val couponInfo: Info
)
