package com.example.weatherapplication.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.weatherapplication.R

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SystemLogging", "AppActivity / onCreate")
        setContentView(R.layout.activity_app)
    }
}