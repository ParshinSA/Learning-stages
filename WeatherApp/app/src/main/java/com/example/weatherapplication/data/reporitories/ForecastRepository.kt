package com.example.weatherapplication.data.reporitories

import com.example.weatherapplication.data.data_source.custom_cities.RoomCustomCitiesDataSource
import com.example.weatherapplication.data.data_source.forecast.RemoteForecastDataSource
import com.example.weatherapplication.data.data_source.forecast.RoomForecastDataSource
import com.example.weatherapplication.data.data_source.shared_preference.SharedPreferenceDataSource
import com.example.weatherapplication.data.database.models.city.City
import com.example.weatherapplication.data.database.models.forecast.Forecast
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val remoteForecastDataSource: RemoteForecastDataSource,
    private val roomForecastDataSource: RoomForecastDataSource,
    private val roomCustomCitiesDataSource: RoomCustomCitiesDataSource,
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) {

    fun requestForecast(latitude: Double, longitude: Double): Observable<Forecast> {
        return remoteForecastDataSource.requestForecast(latitude, longitude)
    }

    fun addForecastInDatabase(forecast: Forecast): Completable {
      return roomForecastDataSource.addForecast(forecast = forecast)
    }

    fun subscribeToForecastDatabase(): Observable<List<Forecast>> {
        return roomForecastDataSource.subscribeToForecastDatabase()
    }

    fun getCustomCities(): Single<List<City>> {
        return roomCustomCitiesDataSource.getCustomCities()
    }

    fun getLastUpdateTime(): Long {
        return sharedPreferenceDataSource.getLastUpdateTime()
    }

    fun saveLastUpdateTime(time: Long) {
        sharedPreferenceDataSource.saveLastUpdateTime(time)
    }
}