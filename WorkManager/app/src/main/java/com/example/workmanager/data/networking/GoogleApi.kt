package com.example.workmanager.data.networking

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface GoogleApi {

    @GET
    suspend fun download(
        @Url url: String
    ): ResponseBody
}