package com.example.weatherapplication.data.reporitories

import com.example.weatherapplication.data.data_source.interf.custom_cities.RemoteCityDataSource
import com.example.weatherapplication.data.data_source.interf.custom_cities.RoomCityDataSource
import com.example.weatherapplication.data.networking.models.city.response.convertToDomainCityDto
import com.example.weatherapplication.domain.models.city.request.DomainRequestSearchByCityNameDto
import com.example.weatherapplication.domain.models.city.response.DomainCityDto
import com.example.weatherapplication.domain.models.city.response.convertToRoomCityDto
import com.example.weatherapplication.domain.models.user_input.convertToRemoteRequestSearchByCityNameDto
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
     * DomainCityDto -> databaseCityDto
     * */
    override fun addCity(domainCityDto: DomainCityDto): Completable {
        return roomDataSource.addCity(
            roomCityDto = domainCityDto.convertToRoomCityDto()
        )
    }

    /**
     * Поиск по названию города
     * Domain -> Network
     * конвертируем:
     * DomainSearchByCityNameDto -> RemoteRequestSearchByCityNameDto
     * Observable<List<RemoteResponseCityDto>> -> Observable<List<DomainCityDto>>
     * */
    override fun searchByCityName(
        domainRequestSearchByCityNameDto: DomainRequestSearchByCityNameDto
    ): Observable<List<DomainCityDto>> {
        return remoteDataSource
            .searchCityByName(
                remoteRequestSearchByCityNameDto = domainRequestSearchByCityNameDto.convertToRemoteRequestSearchByCityNameDto()
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