package com.example.weatherapplication.ui.listofcity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.FragmentListOfCityBinding

class ListOfCityFragment : Fragment(R.layout.fragment_list_of_city) {

    private var _bind: FragmentListOfCityBinding? = null
    private val bind: FragmentListOfCityBinding
        get() = _bind!!

    private lateinit var adapterRV: ListOfCityAdapterRV

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentListOfCityBinding.inflate(layoutInflater, container, false)
        Log.d("SystemLogging", "ListOfCityFragment / onCreate")
        actionInFragment()
        return bind.root
    }

    private fun actionInFragment() {
        initRV()
    }

    private fun initRV() {
        adapterRV = ListOfCityAdapterRV()
        with(bind.listOfCityRV) {
//            adapter = adapterRV
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }


}