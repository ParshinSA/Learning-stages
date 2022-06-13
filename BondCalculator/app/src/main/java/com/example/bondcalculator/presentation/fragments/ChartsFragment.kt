package com.example.bondcalculator.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bondcalculator.databinding.FragmentChartsBinding
import com.example.bondcalculator.domain.instruction.aplication_counter.ApplicationCounter
import com.example.bondcalculator.presentation.AppApplication
import com.example.bondcalculator.presentation.common.ChardsFrgAdapterVp2
import com.example.bondcalculator.presentation.common.NameNestedFragment
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class ChartsFragment : BaseFragment() {

    @Inject
    lateinit var applicationCounter: ApplicationCounter

    private var _binding: FragmentChartsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ChardsFrgAdapterVp2

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
        inject()
        applicationCounter()
        initViewPager2()
        initTabLayout()
    }

    private fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    private fun applicationCounter() {
        binding.buttonSubmitApplication.setOnClickListener {
            applicationCounter.incrementCounter()
        }
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
        adapter = ChardsFrgAdapterVp2(this)
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