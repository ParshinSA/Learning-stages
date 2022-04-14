package com.example.bondcalculator.data.data_source.intf

import com.example.bondcalculator.data.networking.models.RemoteRequestSecuritiesData
import com.example.bondcalculator.data.networking.models.RemoteResponseSecuritiesDataDto
import io.reactivex.Observable

interface RequestSecuritiesDataDataSource {
    fun requestSecuritiesData(requestRemote: RemoteRequestSecuritiesData): Observable<RemoteResponseSecuritiesDataDto>
}