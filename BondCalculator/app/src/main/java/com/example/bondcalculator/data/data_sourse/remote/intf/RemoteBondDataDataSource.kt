package com.example.bondcalculator.data.data_sourse.remote.intf

import com.example.bondcalculator.data.networking.models.bonds_data.RemoteRequestBondsList
import com.example.bondcalculator.data.networking.models.bonds_data.RemoteResponseBondListDto
import com.example.bondcalculator.data.networking.models.payment_calendar.RemoteResponseCouponInfoDto
import com.example.bondcalculator.data.networking.models.payment_calendar.nested_response.RemoteRequestCouponInfo
import io.reactivex.Single

interface RemoteBondDataDataSource {
    fun requestBondList(buBond: RemoteRequestBondsList): Single<RemoteResponseBondListDto>
    fun requestCouponInfo(request: RemoteRequestCouponInfo): Single<RemoteResponseCouponInfoDto>
}