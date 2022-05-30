package com.example.bondcalculator.presentation.ui.charts.nested.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bondcalculator.databinding.FragmentPayoutsBinding
import com.example.bondcalculator.presentation.AppApplication
import com.example.bondcalculator.presentation.ui.charts.nested.NameNestedFragment
import com.example.bondcalculator.presentation.ui.charts.nested.viewmodels.PayoutsViewModel
import com.example.bondcalculator.presentation.ui.charts.nested.viewmodels.viewmodels_factory.PayoutsViewModelFactory
import javax.inject.Inject

class PayoutsFragment : Fragment(), NameNestedFragment {

    private var _binding: FragmentPayoutsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: PayoutsViewModelFactory
    private val viewModel: PayoutsViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ")
        _binding = FragmentPayoutsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        actionInFragment()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun actionInFragment() {
        inject()
        viewModel.start()
    }

    private fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy:")
        super.onDestroy()
    }

    override fun getNameFragment(): String {
        return NAME
    }

    companion object {
        private const val TAG = "PayoutsFragment"
        private const val NAME = "Выплаты"
    }
}