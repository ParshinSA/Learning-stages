package com.example.weatherapplication.data.data_source.impl.forecast

import com.example.weatherapplication.data.data_source.interf.forecast.RemoteForecastDataSource
import com.example.weatherapplication.data.networking.api.ForecastApi
import com.example.weatherapplication.data.networking.models.forecast.RemoteRequestForecastDto
import com.example.weatherapplication.data.networking.models.forecast.RemoteResponseForecastDto
import io.reactivex.Observable
import javax.inject.Inject

class RemoteForecastDataSourceImpl @Inject constructor(
    private val retrofitForecastApi: ForecastApi
) : RemoteForecastDataSource {

    override fun requestForecast(remoteRequestForecastDto: RemoteRequestForecastDto): Observable<RemoteResponseForecastDto> {
        return retrofitForecastApi
            .requestForecast(
                latitude = remoteRequestForecastDto.latitude,
                longitude = remoteRequestForecastDto.longitude
            )
    }

}