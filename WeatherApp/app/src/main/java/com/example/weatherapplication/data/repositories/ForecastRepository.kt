package com.example.weatherapplication.data.repositories

import android.util.Log
import com.example.weatherapplication.data.db.appdb.AppDatabase
import com.example.weatherapplication.data.models.save.SaveForecast
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.data.retrofit.Networking
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ForecastRepository {

    private val forecastDao = AppDatabase.instanceDatabaseModels.getForecastDao()
    private val myGson = GsonBuilder().create()
    private val adapter = myGson.getAdapter(Forecast::class.java)


    suspend fun getForecast(cityId: Int): Forecast? {
        var responseBody: Forecast? = requestForecast(cityId)

        if (responseBody != null) {
            saveForecastInDatabase(cityId, responseBody)
        } else {
            responseBody = getForecastFromDatabase(cityId)
        }
        return responseBody
    }


    private suspend fun requestForecast(cityId: Int): Forecast? {
        return suspendCoroutine { continuation ->
            Networking.weatherApi.requestWeatherForecast(queryCityId = cityId).enqueue(
                object : Callback<Forecast> {

                    override fun onResponse(
                        call: Call<Forecast>, response: Response<Forecast>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            continuation.resume(responseBody)
                        } else {
                            Log.d("SystemLogging", "incorrect code ${response.code()}")
                            continuation.resume(null)
                        }
                    }

                    override fun onFailure(call: Call<Forecast>, t: Throwable) {
                        Log.d("SystemLogging", "throwable:$t ")
                        continuation.resume(null)
                    }
                }
            )
        }
    }

    private suspend fun saveForecastInDatabase(cityId: Int, responseBody: Forecast?) {
        val toJson = adapter.toJson(responseBody)
        forecastDao.insertListForecast(SaveForecast(cityId, toJson))
    }

    suspend fun getForecastFromDatabase(cityId: Int): Forecast? {
        val jsonDb: String? = forecastDao.getWeatherForecast(cityId)?.forecastJson
        Log.d("SystemLogging", "getForecastDb jsonDb:$jsonDb ")
        return jsonDb?.let { adapter.fromJson(it) }
    }
}