package com.example.weatherapplication.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weatherapplication.R
import com.example.weatherapplication.data.objects.AppDisposable
import com.example.weatherapplication.data.objects.AppState
import com.example.weatherapplication.data.repositories.RemoteRepository
import com.example.weatherapplication.databinding.ActivityAppBinding
import com.google.android.material.transition.MaterialElevationScale

class AppActivity : AppCompatActivity() {
    private var _bind: ActivityAppBinding? = null
    private val bind: ActivityAppBinding
        get() = _bind!!

    private val currentNavigationFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.frg_nav_host)
            ?.childFragmentManager
            ?.fragments
            ?.first()

    override fun onStart() {
        AppState.appIsCollapsed(false)
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bind = ActivityAppBinding.inflate(layoutInflater)
        setContentView(bind.root)

        exitEnterTransition()
    }

    private fun exitEnterTransition() {
        currentNavigationFragment?.apply {
            exitTransition = MaterialElevationScale(false).apply {
                duration = 300
            }
            reenterTransition = MaterialElevationScale(true).apply {
                duration = 300
            }
        }
    }

    override fun onStop() {
        Log.d(TAG, "onStop: ")
        AppState.appIsCollapsed(true)
        RemoteRepository(this).periodUpdateForecastAllCityList()
        super.onStop()
    }

    override fun onDestroy() {
        AppDisposable.unSubscribeAll()
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
    }

    companion object {
        const val TAG = "AppActivity_Logging"
    }
}