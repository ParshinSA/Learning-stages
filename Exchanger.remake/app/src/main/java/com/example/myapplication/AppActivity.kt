package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AppActivity : AppCompatActivity(R.layout.activity_app)  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null ){
            supportFragmentManager.beginTransaction()
                    .replace(R.id.app_container, ExchangeFragment())
                    .commit()
        }
    }
}