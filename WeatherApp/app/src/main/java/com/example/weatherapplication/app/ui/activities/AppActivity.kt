package com.example.weatherapplication.app.ui.activities

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapplication.R
import com.example.weatherapplication.app.common.AppState
import com.example.weatherapplication.data.data_source.forecast.RemoteForecastDataSource
import com.example.weatherapplication.app.ui.AppApplication
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AppActivity : AppCompatActivity(R.layout.activity_app) {

    private lateinit var remoteRepository: RemoteForecastDataSource
    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var appState: AppState

    override fun onStart() {
        inject()
        changeStateCollapsedApp(false)
        super.onStart()
    }

    @Inject
    fun injectDependency(
        remoteRepository: RemoteForecastDataSource,
        compositeDisposable: CompositeDisposable,
        appState: AppState
    ) {
        this.remoteRepository = remoteRepository
        this.compositeDisposable = compositeDisposable
        this.appState = appState
    }

    private fun inject() {
        (applicationContext as AppApplication).appComponent.inject(this)
    }

    private fun changeStateCollapsedApp(isCollapsed: Boolean) {
        appState.changeStateApp(isCollapsed)
    }

    private fun startBackgroundUpdateForecast() {
        remoteRepository.periodUpdateForecastAllCityList()
    }

    private fun unsubscribeAll() {
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