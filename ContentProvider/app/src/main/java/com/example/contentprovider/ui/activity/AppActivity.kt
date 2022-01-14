package com.example.contentprovider.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contentprovider.R

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
    }
}