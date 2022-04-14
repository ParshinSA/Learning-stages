package com.example.bondcalculator.data.data_source.impl

import com.example.bondcalculator.data.data_source.intf.RequestSecuritiesDataDataSource
import com.example.bondcalculator.data.networking.api.SecuritiesDataApi
import com.example.bondcalculator.data.networking.models.RemoteRequestSecuritiesData
import com.example.bondcalculator.data.networking.models.RemoteResponseSecuritiesDataDto
import io.reactivex.Observable
import javax.inject.Inject

class RequestSecuritiesDataDataSourceImpl @Inject constructor(
    private val securitiesDataApi: SecuritiesDataApi
) : RequestSecuritiesDataDataSource {

    override fun requestSecuritiesData(requestRemote: RemoteRequestSecuritiesData): Observable<RemoteResponseSecuritiesDataDto> {
        return securitiesDataApi.getRussianBondsData(requestRemote.boardId.name)
    }

}