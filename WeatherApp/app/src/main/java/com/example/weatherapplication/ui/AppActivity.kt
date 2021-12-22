package com.example.weatherapplication.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.ActivityAppBinding
import com.example.weatherapplication.ui.weather.detailsforecast.DetailsForecastFragment
import com.example.weatherapplication.ui.weather.shortforecastlist.ShortForecastListFragment
import com.google.android.material.transition.MaterialElevationScale

class AppActivity : AppCompatActivity() {
    private var _bind: ActivityAppBinding? = null
    private val bind: ActivityAppBinding
        get() = _bind!!

    private val currentNavigationFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment)
            ?.childFragmentManager
            ?.fragments
            ?.first()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bind = ActivityAppBinding.inflate(layoutInflater)
        setContentView(bind.root)

        generateReportWeatherInCity()
        exitEnterTransition()
    }

    private fun generateReportWeatherInCity() {
        bind.openReportFab.setOnClickListener {
            bind.openReportFab.visibility = View.INVISIBLE

            val extras = getMyExtras()
            val idDirection = getMyDirection()
            val cityName = getCityName(idDirection)

            val bundle = Bundle()
            bundle.putInt(KEY_ID_DIRECTION, idDirection)
            bundle.putString(KEY_CITY_NAME, cityName)

            findNavController(R.id.my_nav_host_fragment).navigate(
                idDirection,
                bundle,
                null,
                extras
            )
        }
    }

    private fun getCityName(idDirection: Int): String {
        return if (idDirection == R.id.action_detailsForecastFragment_to_weatherReportFragment)
            findViewById<TextView>(R.id.city_name_TV).text.toString()
        else ""
    }

    private fun getMyDirection(): Int {
        return when (currentNavigationFragment) {
            is ShortForecastListFragment -> {
                R.id.action_shortForecastListFragment_to_weatherReportFragment
            }
            is DetailsForecastFragment -> {
                R.id.action_detailsForecastFragment_to_weatherReportFragment
            }
            else -> error("Incorrect ID direction")
        }
    }

    private fun getMyExtras(): FragmentNavigator.Extras {
        val transitionName =
            resources.getString(R.string.WeatherReportFragment_transition_name)
        return FragmentNavigatorExtras(bind.openReportFab to transitionName)
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

    companion object {
        const val KEY_ID_DIRECTION = "key ID direction"
        const val KEY_CITY_NAME = "key city name"
    }
}