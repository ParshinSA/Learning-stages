package com.example.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.example.sharedprefs.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _bind: ActivityMainBinding? = null
    private val bind: ActivityMainBinding
        get() = _bind!!

    private var mColor: Int = R.color.default_color
        set(value) {
            field = value
            setColorView(field)
        }

    private var mCounter = 0
        set(value) {
            field = value
            updateCounterView()
        }

    private lateinit var mPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bind = ActivityMainBinding.inflate(layoutInflater)
        val view = bind.root
        setContentView(view)

        actionInActivity()
    }

    private fun actionInActivity() {
        sharedPref()
        startSettings()
        changeCounter()
        changeBackgroundView()
        resetSettings()
    }

    private fun sharedPref() {
        mPreferences = getSharedPreferences(SHARED_PREF_FILE, Context.MODE_PRIVATE)
    }

    private fun resetSettings() {
        bind.btnReset.setOnClickListener {
            mPreferences.edit().clear().apply()
            mColor = R.color.default_color
            mCounter = 0
        }
    }

    private fun startSettings() {
        mColor = mPreferences.getInt(COLOR_KEY, R.color.default_color)
        mCounter = mPreferences.getInt(COUNT_KEY, 0)
    }

    private fun changeCounter() {
        bind.btnCount.setOnClickListener {
            mCounter++
        }
    }

    private fun updateCounterView() {
        bind.counterView.text = mCounter.toString()
    }

    private fun changeBackgroundView() {
        onClickBtn(bind.btnBlackSettings)
        onClickBtn(bind.btnBlueSettings)
        onClickBtn(bind.btnRedSettings)
        onClickBtn(bind.btnGreenSettings)
    }

    private fun onClickBtn(btn: Button) {
        btn.setOnClickListener {
            when (btn.id) {
                R.id.btn_black_settings -> mColor = R.color.black
                R.id.btn_red_settings -> mColor = R.color.red
                R.id.btn_blue_settings -> mColor = R.color.blue
                R.id.btn_green_settings -> mColor = R.color.green
            }
        }
    }

    private fun setColorView(@ColorRes colorRes: Int) {
        bind.counterView.setBackgroundColor(ContextCompat.getColor(this, colorRes))
    }

    override fun onPause() {
        super.onPause()
        val sharedEditor = mPreferences.edit()
        sharedEditor.putInt(COUNT_KEY, mCounter)
        sharedEditor.putInt(COLOR_KEY, mColor)
        sharedEditor.apply()
    }

    companion object {
        const val COUNT_KEY = "count"
        const val COLOR_KEY = "color"
        const val SHARED_PREF_FILE = "com.example.android.sharedprefs"
    }
}