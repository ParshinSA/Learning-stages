package com.example.weatherapplication.data.data_source.interf.forecast

import com.example.weatherapplication.data.networking.models.forecast.request.RemoteRequestForecastDto
import com.example.weatherapplication.data.networking.models.forecast.response.RemoteResponseForecastDto
import io.reactivex.Observable

interface RemoteForecastDataSource {

    fun requestForecast(remoteRequestForecastDto: RemoteRequestForecastDto): Observable<RemoteResponseForecastDto>

}