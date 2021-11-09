package com.example.roomwordssample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.roomwordssample.R
import com.example.roomwordssample.data.db.word_db.DataBase
import com.example.roomwordssample.ui.word.WordViewModel

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        Log.d("AppLogging", "onCreate AppActivity")
    }

}