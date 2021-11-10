package com.example.weatherapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.weatherapplication.R
import com.example.weatherapplication.data.datetime.DateTimeFormat
import com.example.weatherapplication.data.datetime.DateTimeViewModel
import com.example.weatherapplication.databinding.ActivityAppBinding
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SystemLogging", "AppActivity / onCreate")
        initData()
        setContentView(R.layout.activity_app)
    }

    private fun initData() {
        TODO("Not yet implemented")
    }
}