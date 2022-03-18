package com.example.weatherapplication//package com.example.weatherapplication.repositories
//
//import android.util.Log
//import androidx.work.*
//import com.example.weatherapplication.data.data_source.RemoteDataSource
//import com.example.weatherapplication.data.db.models.city.City
//import com.example.weatherapplication.data.db.models.forecast.Forecast
//import com.example.weatherapplication.data.db.models.report.DataHistory
//import com.example.weatherapplication.data.db.models.report.FieldValue
//import com.example.weatherapplication.data.db.models.report.StatisticalWeather
//import com.example.weatherapplication.data.networking.api.CoordinationApi
//import com.example.weatherapplication.data.networking.api.ForecastApi
//import com.example.weatherapplication.data.networking.api.HistoryApi
//import com.example.weatherapplication.domain.ReportPeriods
//import com.example.weatherapplication.domain.ReportPeriods.TEN_DAYS
//import com.example.weatherapplication.app.workers.UpdateForecastWorker
//import io.reactivex.Observable
//import io.reactivex.Single
//import io.reactivex.schedulers.Schedulers
//import java.text.SimpleDateFormat
//import java.util.*
//import java.util.concurrent.TimeUnit
//import javax.inject.Inject
//
//class RemoteRepository @Inject constructor(
//    private val workManager: WorkManager,
//    private val retrofitForecastApi: ForecastApi,
//    private val retrofitHistoryApi: HistoryApi,
//    private val retrofitCoordinationApi: CoordinationApi
//) : RemoteDataSource {
//
//    override fun oneTimeUpdateForecastAllCity() {
//        Log.d(TAG, "oneTimeUpdateForecastAllCity: start")
//
//        workManager.enqueueUniqueWork(
//            UpdateForecastWorker.UPDATE_FORECAST_WORKER_NAME,
//            ExistingWorkPolicy.REPLACE,
//
//            OneTimeWorkRequestBuilder<UpdateForecastWorker>()
//                .build()
//        )
//    }
//
//    override fun requestForecastAllCity(cityList: List<City>): Observable<Forecast> {
//        return Observable.fromIterable(cityList)
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .flatMap {
//                retrofitForecastApi.requestForecast(it.latitude, it.longitude)
//            }
//    }
//
//    override fun periodUpdateForecastAllCityList() {
//        Log.d(TAG, "periodUpdateForecastAllCityList: start")
//        workManager.enqueueUniquePeriodicWork(
//            UpdateForecastWorker.UPDATE_FORECAST_WORKER_NAME,
//            ExistingPeriodicWorkPolicy.REPLACE,
//
//            PeriodicWorkRequestBuilder<UpdateForecastWorker>(15, TimeUnit.MINUTES)
//                .setConstraints(
//                    Constraints.Builder()
//                        .setRequiredNetworkType(NetworkType.CONNECTED)
//                        .build()
//                )
//                .build()
//        )
//    }
//
//    override fun searchCity(userInput: String): Single<List<City>> {
//        Log.d(TAG, "searchCity: $userInput")
//        return retrofitCoordinationApi.getCoordinationByNameCity(userInput)
//    }
//
//    override fun requestHistoryMonth(forecast: Forecast, step: Int): Observable<StatisticalWeather> {
//        return retrofitHistoryApi.requestHistoryMonth(
//            forecast.coordination.latitude,
//            forecast.coordination.longitude,
//            calculateMonthStepMonth(step) // step 30 day
//        )
//    }
//
//    override fun requestHistoryDay(forecast: Forecast, step: Int): Observable<StatisticalWeather> {
//        return retrofitHistoryApi.requestHistoryDay(
//            forecast.coordination.latitude,
//            forecast.coordination.longitude,
//            calculateDayStepDay(step),
//            calculateMonthStepDay(step)
//        )
//    }
//
//    private fun calculateDayStepDay(step: Int): Int {
//        return SimpleDateFormat("dd", Locale("ru"))
//            .format(System.currentTimeMillis() - 86400000 * step).toInt()
//    }
//
//    private fun calculateMonthStepDay(step: Int): Int {
//        return SimpleDateFormat("MM", Locale("ru"))
//            .format(System.currentTimeMillis() - 86400000 * step).toInt()
//    }
//
//    private fun calculateMonthStepMonth(step: Int): Int {
//        return SimpleDateFormat("MM", Locale("ru"))
//            .format(System.currentTimeMillis() - 2592000000 * step).toInt()
//    }
//
//    companion object {
//        const val TAG = "RemoteRepo_Logging"
//    }
//}
//
