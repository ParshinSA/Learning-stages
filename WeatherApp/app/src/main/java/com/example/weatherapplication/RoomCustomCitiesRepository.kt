package com.example.weatherapplication//package com.example.weatherapplication.repositories
//
//import com.example.weatherapplication.data.db.custom_cities_db.CustomCitiesDao
//import com.example.weatherapplication.data.db.models.city.City
//import com.example.weatherapplication.data.data_source.custom_cities.RoomCustomCitiesDataSource
//import io.reactivex.Completable
//import io.reactivex.Observable
//import io.reactivex.schedulers.Schedulers
//import javax.inject.Inject
//
//class RoomCustomCitiesRepository @Inject constructor(
//    private val citiesDao: CustomCitiesDao
//) : RoomCustomCitiesDataSource {
//
//    override fun observableCityList(): Observable<List<City>> {
//        return citiesDao.getCityList()
//            .subscribeOn(Schedulers.io())
//    }
//
//    override fun addCity(city: City): Completable {
//        return Completable.create { subscriber ->
//            citiesDao.addCity(city)
//            subscriber.onComplete()
//        }
//            .subscribeOn(Schedulers.io())
//    }
//}