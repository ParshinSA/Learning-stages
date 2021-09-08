package com.example.exchanger

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AppActivity : AppCompatActivity(R.layout.activity_app) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null ){
            supportFragmentManager.beginTransaction()
                .add(R.id.app_container, ExchangeFragment())
                .commit()
        }
    }
}