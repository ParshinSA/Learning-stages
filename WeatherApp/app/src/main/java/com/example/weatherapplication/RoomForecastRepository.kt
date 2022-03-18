package com.example.weatherapplication//package com.example.weatherapplication.repositories
//
//import com.example.weatherapplication.data.db.forecast_db.ForecastDao
//import com.example.weatherapplication.data.db.models.forecast.Forecast
//import com.example.weatherapplication.data.data_source.forecast.RoomForecastDataSource
//import io.reactivex.Observable
//import io.reactivex.schedulers.Schedulers
//import java.util.concurrent.TimeUnit
//import javax.inject.Inject
//
//class RoomForecastRepository @Inject constructor(
//    private val forecastDao: ForecastDao,
//) : RoomForecastDataSource {
//
//    override fun saveForecast(forecast: Forecast) {
//        forecastDao.insertForecast(forecast)
//    }
//
//    override fun observeForecastList(): Observable<List<Forecast>> {
//        return forecastDao.getListForecast()
//            .observeOn(Schedulers.io())
//            .debounce(500, TimeUnit.MILLISECONDS)
//    }
//
//    companion object {
//        const val TAG = "ForecastDBRepo_Logging"
//    }
//
//}