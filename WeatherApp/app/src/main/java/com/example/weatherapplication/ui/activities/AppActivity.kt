package com.example.weatherapplication.ui.activities

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapplication.R
import com.example.weatherapplication.common.AppState
import com.example.weatherapplication.data.repositories.repo_interface.RemoteRepository
import com.example.weatherapplication.ui.AppApplication
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AppActivity : AppCompatActivity(R.layout.activity_app) {

    @Inject
    lateinit var remoteRepository: RemoteRepository
    @Inject
    lateinit var compositeDisposable: CompositeDisposable
    @Inject
    lateinit var appState: AppState

    override fun onStart() {
        inject()
        changeStateCollapsedApp(false)
        super.onStart()
    }

    private fun inject() {
        (applicationContext as AppApplication).appComponent.inject(this)
    }

    private fun changeStateCollapsedApp(isCollapsed: Boolean) {
        appState.changeStateApp(isCollapsed)
    }

    private fun startBackgroundUpdateForecast(){
        remoteRepository.periodUpdateForecastAllCityList()
    }

    private fun unsubscribeAll(){
        compositeDisposable.clear()
    }

    override fun onStop() {
        Log.d(TAG, "onStop: ")
        changeStateCollapsedApp(true)
        super.onStop()
    }

    override fun onDestroy() {
        unsubscribeAll()
        startBackgroundUpdateForecast()
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
    }

    companion object {
        const val TAG = "AppActivity_Logging"
    }
}