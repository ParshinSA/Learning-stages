package com.example.bondcalculator.data.data_source.impl

import com.example.bondcalculator.data.data_source.intf.RemoteBondDataDataSource
import com.example.bondcalculator.data.networking.api.SecuritiesDataApi
import com.example.bondcalculator.data.networking.models.bonds_data.RemoteRequestBondsList
import com.example.bondcalculator.data.networking.models.bonds_data.RemoteResponseBondListDto
import com.example.bondcalculator.data.networking.models.payment_calendar.RemoteResponseCouponInfoDto
import com.example.bondcalculator.data.networking.models.payment_calendar.nested_response.RemoteRequestCouponInfo
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class RemoteBondDataDataSourceImpl @Inject constructor(
    private val securitiesDataApi: SecuritiesDataApi
) : RemoteBondDataDataSource {

    override fun requestBondList(buBond: RemoteRequestBondsList): Single<RemoteResponseBondListDto> {
        return securitiesDataApi.getSecuritiesData(buBond.boardId)
    }

    override fun requestCouponInfo(request: RemoteRequestCouponInfo): Single<RemoteResponseCouponInfoDto> {
        return securitiesDataApi.getCouponInfo(request.securityId)
    }

}