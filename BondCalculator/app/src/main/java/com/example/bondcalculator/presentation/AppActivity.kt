package com.example.bondcalculator.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bondcalculator.R
import com.example.bondcalculator.databinding.ActivityAppBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class AppActivity : AppCompatActivity(R.layout.activity_app) {

    private val binding by viewBinding(ActivityAppBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupController()
    }

    private fun setupController() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.navHostFragmentActivityApp)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nameMenuAndControlSelection,
                R.id.nameMenuAndControlCharts,
                R.id.nameMenuAndControlComposition
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

}