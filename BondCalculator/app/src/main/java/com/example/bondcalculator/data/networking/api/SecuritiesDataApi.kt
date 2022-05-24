package com.example.bondcalculator.data.networking.api

import com.example.bondcalculator.data.networking.models.bonds_data.RemoteResponseBondListDto
import com.example.bondcalculator.data.networking.models.payment_calendar.RemoteResponseCouponInfoDto
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface SecuritiesDataApi {

    @GET("iss/engines/stock/markets/bonds/boards/{boardId}/securities.json")
    fun getSecuritiesData(
        @Path("boardId") boardId: String
    ): Single<RemoteResponseBondListDto>

    @GET("iss/securities/{securityTd}/bondization.json")
    fun getCouponInfo(
        @Path("securityTd") securityId: String
    ): Single<RemoteResponseCouponInfoDto>
}