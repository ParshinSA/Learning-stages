package com.example.weatherapplication.data.networking.models.report.response

import com.example.weatherapplication.data.networking.models.report.response.nesteds.ReportData
import com.google.gson.annotations.SerializedName

data class RemoteResponseReportDto(

    @SerializedName("result")
    val reportData: ReportData

)