package com.example.bondcalculator.presentation.ui.charts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bondcalculator.databinding.FragmentChartsBinding
import com.example.bondcalculator.presentation.ui.charts.nested.MyViewPager2Adapter

class ChartsFragment : Fragment() {

    private var _binding: FragmentChartsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MyViewPager2Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChartsBinding.inflate(inflater, container, false)
        initComponents()
        return binding.root
    }

    private fun initComponents() {

    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}