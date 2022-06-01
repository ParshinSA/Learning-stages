package com.example.bondcalculator.presentation.ui.charts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bondcalculator.databinding.FragmentChartsBinding
import com.example.bondcalculator.presentation.ui.charts.nested.common.MyViewPager2Adapter
import com.example.bondcalculator.presentation.ui.charts.nested.common.NameNestedFragment
import com.google.android.material.tabs.TabLayoutMediator

class ChartsFragment : Fragment() {

    private var _binding: FragmentChartsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MyViewPager2Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ")
        _binding = FragmentChartsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        actionInFragment()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun actionInFragment() {
        initViewPager2()
        initTabLayout()
    }

    private fun initTabLayout() {
        TabLayoutMediator(
            binding.tabLayoutCharts,
            binding.viewPager2Fragments
        ) { tab, position ->
            tab.text = (adapter.createFragment(position) as? NameNestedFragment)?.getNameFragment()
        }.attach()
    }

    private fun initViewPager2() {
        adapter = MyViewPager2Adapter(this)
        binding.viewPager2Fragments.adapter = adapter
    }

    override fun onDestroy() {
        _binding = null
        Log.d(TAG, "onDestroy:")
        super.onDestroy()
    }

    companion object {
        private const val TAG = "ChartsFragment"
    }
}