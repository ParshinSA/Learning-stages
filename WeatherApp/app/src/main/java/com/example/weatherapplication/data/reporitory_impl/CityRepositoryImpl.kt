package com.example.weatherapplication.data.reporitory_impl

import com.example.weatherapplication.data.data_source.interf.custom_cities.RemoteCityDataSource
import com.example.weatherapplication.data.data_source.interf.custom_cities.RoomCityDataSource
import com.example.weatherapplication.data.database.models.city.convertToRoomCityDto
import com.example.weatherapplication.data.networking.models.city.convertToDomainCityDto
import com.example.weatherapplication.data.networking.models.city.convertToRemoteRequestSearchByCityNameDto
import com.example.weatherapplication.domain.models.city.DomainCity
import com.example.weatherapplication.domain.models.city.DomainRequestSearchByCityName
import com.example.weatherapplication.domain.repository.CityRepository
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteCityDataSource,
    private val roomDataSource: RoomCityDataSource
) : CityRepository {


    /**
     * Добавление города в базу данных
     * Domain -> Database
     * конвертируем:
     * DomainCity -> RoomCityDto
     * */
    override fun addCity(domainCity: DomainCity): Completable {
        return roomDataSource.addCity(
            roomCityDto = domainCity.convertToRoomCityDto()
        )
    }

    /**
     * Поиск по названию города
     * Domain -> Network
     * конвертируем:
     * DomainRequestSearchByCityName -> RemoteRequestSearchByCityNameDto
     * Observable<List<RemoteResponseCityDto>> -> Observable<List<DomainCity>>
     * */
    override fun searchByCityName(
        domainRequestSearchByCityName: DomainRequestSearchByCityName
    ): Observable<List<DomainCity>> {
        return remoteDataSource
            .searchCityByName(
                remoteRequestSearchByCityNameDto = domainRequestSearchByCityName.convertToRemoteRequestSearchByCityNameDto()
            )
            .flatMap { listRemoteResponseCityDto ->
                Observable.fromIterable(listRemoteResponseCityDto)
                    .map { remoteResponseCityDto ->
                        remoteResponseCityDto.convertToDomainCityDto()
                    }
                    .buffer(listRemoteResponseCityDto.size)
            }
    }

}