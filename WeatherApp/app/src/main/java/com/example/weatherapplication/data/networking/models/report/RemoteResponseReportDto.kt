package com.example.weatherapplication.data.networking.models.report

import com.example.weatherapplication.data.networking.models.report.nested_response.ReportData
import com.google.gson.annotations.SerializedName

data class RemoteResponseReportDto(

    @SerializedName("result")
    val reportData: ReportData

)