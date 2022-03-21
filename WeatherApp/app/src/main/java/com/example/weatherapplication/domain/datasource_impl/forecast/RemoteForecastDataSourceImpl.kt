package com.example.weatherapplication.domain.datasource_impl.forecast

import com.example.weatherapplication.data.data_source.forecast.RemoteForecastDataSource
import com.example.weatherapplication.data.database.models.forecast.Forecast
import com.example.weatherapplication.data.networking.api.ForecastApi
import io.reactivex.Observable
import javax.inject.Inject

class RemoteForecastDataSourceImpl @Inject constructor(
    private val retrofitForecastApi: ForecastApi
) : RemoteForecastDataSource {

    override fun requestForecast(latitude: Double, longitude: Double): Observable<Forecast> {
        return retrofitForecastApi.requestForecast(latitude, longitude)
    }

}