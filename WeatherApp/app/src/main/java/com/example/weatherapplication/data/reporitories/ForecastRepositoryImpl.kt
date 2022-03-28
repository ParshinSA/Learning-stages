package com.example.weatherapplication.data.reporitories

import android.util.Log
import com.example.weatherapplication.data.data_source.interf.custom_cities.RoomCityDataSource
import com.example.weatherapplication.data.data_source.interf.forecast.RemoteForecastDataSource
import com.example.weatherapplication.data.data_source.interf.forecast.RoomForecastDataSource
import com.example.weatherapplication.data.data_source.interf.shared_preference.SharedPreferenceDataSource
import com.example.weatherapplication.data.database.models.city.convertToDomainCity
import com.example.weatherapplication.data.database.models.forecast.convertToDomainForecast
import com.example.weatherapplication.data.database.models.forecast.convertToRoomForecastDto
import com.example.weatherapplication.data.networking.models.forecast.request.convertToRemoteRequestForecastDto
import com.example.weatherapplication.data.networking.models.forecast.response.convertToDomainForecastDto
import com.example.weatherapplication.domain.models.city.DomainCity
import com.example.weatherapplication.domain.models.forecast.DomainForecast
import com.example.weatherapplication.domain.models.update_time.DomainLastTimeUpdate
import com.example.weatherapplication.domain.repository.ForecastRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val remoteForecastDataSource: RemoteForecastDataSource,
    private val roomForecastDataSource: RoomForecastDataSource,
    private val roomCityDataSource: RoomCityDataSource,
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : ForecastRepository {

    override fun requestForecast(domainCity: DomainCity): Observable<DomainForecast> {
        return remoteForecastDataSource.requestForecast(
            domainCity.convertToRemoteRequestForecastDto()
        ).map { remoteResponseForecastDto ->
            remoteResponseForecastDto.convertToDomainForecastDto()
        }
    }

    override fun addForecastInDatabase(domainForecast: DomainForecast): Completable {
        return roomForecastDataSource.addForecast(
            domainForecast.convertToRoomForecastDto()
        )
    }

    override fun getListForecastFromDatabase(): Observable<List<DomainForecast>> {
        return roomForecastDataSource.getListForecastFromDatabase()
            .flatMap { listDatabaseForecastDto ->
                if (listDatabaseForecastDto.isNotEmpty()) {
                    Observable.fromIterable(listDatabaseForecastDto)
                        .map { roomForecastDto ->
                            roomForecastDto.convertToDomainForecast()
                        }
                        .buffer(listDatabaseForecastDto.size)
                } else {
                    Observable.empty()
                }
            }
    }

    override fun getListCity(): Observable<List<DomainCity>> {
        return roomCityDataSource.getCity()
            .flatMap { listRoomCityDto ->
                if (listRoomCityDto.isNotEmpty()) {
                    Observable.fromIterable(listRoomCityDto)
                        .map { roomCityDto ->
                            roomCityDto.convertToDomainCity()
                        }
                        .buffer(listRoomCityDto.size)
                } else {
                    Log.d(TAG, "getListCity: empty")
                    Observable.empty()
                }
            }
    }

    override fun getLastUpdateTime(): DomainLastTimeUpdate {
        return sharedPreferenceDataSource.getLastUpdateTime()
    }

    override fun saveLastUpdateTime() {
        sharedPreferenceDataSource.saveLastUpdateTime()
    }

    companion object{
        const val TAG = "ForecastRepo_Logging"
    }
}