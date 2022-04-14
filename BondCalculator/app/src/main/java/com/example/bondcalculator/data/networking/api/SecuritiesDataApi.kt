package com.example.bondcalculator.data.networking.api

import com.example.bondcalculator.data.networking.models.RemoteResponseSecuritiesDataDto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface SecuritiesDataApi {

    @GET("iss/engines/stock/markets/bonds/boards/{boardId}/securities.json")
    fun getRussianBondsData(
        @Path("boardId") boardId: String
    ): Observable<RemoteResponseSecuritiesDataDto>

}