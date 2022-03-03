package com.example.weatherapplication.ui

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapplication.R
import com.example.weatherapplication.data.objects.AppDisposable
import com.example.weatherapplication.data.objects.AppState
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import javax.inject.Inject

class AppActivity : AppCompatActivity(R.layout.activity_app) {

    @Inject
    lateinit var remoteRepository: RemoteRepository

    @Inject
    lateinit var appDisposable: AppDisposable

    @Inject
    lateinit var appState: AppState

    override fun onStart() {
        inject()
        appState.appIsCollapsed(false)
        super.onStart()
    }

    private fun inject() {
        (applicationContext as AppApplication).appComponent.inject(this)
    }

    override fun onStop() {
        Log.d(TAG, "onStop: ")
        appState.appIsCollapsed(true)
        remoteRepository.periodUpdateForecastAllCityList()
        super.onStop()
    }

    override fun onDestroy() {
        appDisposable.unSubscribeAll()
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
    }

    companion object {
        const val TAG = "AppActivity_Logging"
    }
}