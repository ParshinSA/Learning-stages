package com.example.bondcalculator.presentation.ui.charts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bondcalculator.databinding.FragmentChartsBinding
import com.example.bondcalculator.presentation.AppApplication
import com.example.bondcalculator.presentation.ui.charts.nested.MyViewPager2Adapter
import com.example.bondcalculator.presentation.viewmodels.ChartsViewModel
import com.example.bondcalculator.presentation.viewmodels.factory.ChartsViewModelFactory
import javax.inject.Inject

class ChartsFragment : Fragment() {

    private var _binding: FragmentChartsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ChartsViewModelFactory
    private val viewModel: ChartsViewModel by viewModels { viewModelFactory }

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
        inject()
        initViewPager2()
        observeData()
    }

    private fun initViewPager2() {
        adapter = MyViewPager2Adapter(this)
        binding.viewPager2Fragments.adapter = adapter
    }

    private fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    private fun observeData() {
        viewModel.log()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}