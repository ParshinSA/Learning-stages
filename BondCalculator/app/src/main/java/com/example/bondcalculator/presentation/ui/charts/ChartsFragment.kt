package com.example.bondcalculator.presentation.ui.charts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bondcalculator.R
import com.example.bondcalculator.databinding.FragmentChartsBinding

class ChartsFragment : Fragment(R.layout.fragment_charts) {

    private val binding by viewBinding(FragmentChartsBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initComponents()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun initComponents() {

    }
}