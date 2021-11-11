package com.example.weatherapplication.ui.listofcity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.FragmentListOfCityBinding

class ListOfCityFragment : Fragment(R.layout.fragment_list_of_city) {

    private var _bind: FragmentListOfCityBinding? = null
    private val bind: FragmentListOfCityBinding
        get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("SystemLogging", "ListOfCityFragment / onCreate")
        _bind = FragmentListOfCityBinding.inflate(layoutInflater, container, false)
        return bind.root
    }

}