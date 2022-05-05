package com.example.bondcalculator.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bondcalculator.R
import com.example.bondcalculator.databinding.ActivityAppBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class AppActivity : AppCompatActivity() {

    private var _binding: ActivityAppBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupController()
    }

    private fun setupController() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.navHostFragmentActivityApp)
        navView.setupWithNavController(navController)
    }

}