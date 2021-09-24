package com.example.myapplication

import android.util.Log
import com.example.myapplication.currency.AllValute
import com.example.myapplication.currency.ObjectValute
import com.example.myapplication.retrofitgson.NetworkRetrofit
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryCurrency {
    fun requestCourse(
        callbackListCurse: (AllValute) -> Unit
    ) {
        Thread {
            try {
                NetworkRetrofit.netTestV.getCourse().enqueue(
                    object : Callback<ObjectValute> {
                        override fun onResponse(
                            call: Call<ObjectValute>,
                            response: Response<ObjectValute>
                        ) {
                            val responseBody = response.body()?.valute!!
                            callbackListCurse(responseBody)
                        }

                        override fun onFailure(call: Call<ObjectValute>, t: Throwable) {
                            Log.d("MyServer", "t $t")
                        }
                    }
                )
            } catch (e: IOException) {
                Log.d("MyServer", "Error response e:${e.message}")
            }
        }.start()

    }
}
