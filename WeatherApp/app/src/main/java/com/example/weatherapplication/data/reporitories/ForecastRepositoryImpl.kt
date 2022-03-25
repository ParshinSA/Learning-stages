package com.example.weatherapplication.data.reporitories

import com.example.weatherapplication.data.data_source.interf.custom_cities.RoomCityDataSource
import com.example.weatherapplication.data.data_source.interf.forecast.RemoteForecastDataSource
import com.example.weatherapplication.data.data_source.interf.forecast.RoomForecastDataSource
import com.example.weatherapplication.data.data_source.interf.shared_preference.SharedPreferenceDataSource
import com.example.weatherapplication.data.database.models.city.convertToDomainCityDto
import com.example.weatherapplication.data.database.models.forecast.convertToDomainForecastDto
import com.example.weatherapplication.data.networking.models.forecast.response.convertToDomainForecastDto
import com.example.weatherapplication.domain.models.city.response.DomainCityDto
import com.example.weatherapplication.domain.models.city.response.convertToRemoteRequestForecastDto
import com.example.weatherapplication.domain.models.forecast.DomainForecastDto
import com.example.weatherapplication.domain.models.forecast.convertToRoomForecastDto
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

    override fun requestForecast(domainCityDto: DomainCityDto): Observable<DomainForecastDto> {
        return remoteForecastDataSource.requestForecast(
            domainCityDto.convertToRemoteRequestForecastDto()
        ).map { remoteResponseForecastDto ->
            remoteResponseForecastDto.convertToDomainForecastDto()
        }
    }

    override fun addForecastInDatabase(domainForecastDto: DomainForecastDto): Completable {
        return roomForecastDataSource.addForecast(
            domainForecastDto.convertToRoomForecastDto()
        )
    }

    override fun getListForecastFromDatabase(): Observable<List<DomainForecastDto>> {
        return roomForecastDataSource.getListForecastFromDatabase()
            .flatMap { listDatabaseForecastDto ->
                Observable.fromIterable(listDatabaseForecastDto)
                    .map { databaseForecastDto ->
                        databaseForecastDto.convertToDomainForecastDto()
                    }
                    .buffer(listDatabaseForecastDto.size)
            }
    }

    override fun getListCity(): Observable<List<DomainCityDto>> {
        return roomCityDataSource.getCity()
            .flatMap { listRoomCityDto ->
                Observable.fromIterable(listRoomCityDto)
                    .map { roomCityDto ->
                        roomCityDto.convertToDomainCityDto()
                    }
                    .buffer(listRoomCityDto.size)
            }
    }

    override fun getLastUpdateTime(): DomainLastTimeUpdate {
        return sharedPreferenceDataSource.getLastUpdateTime()
    }

    override fun saveLastUpdateTime() {
        sharedPreferenceDataSource.saveLastUpdateTime()
    }
}