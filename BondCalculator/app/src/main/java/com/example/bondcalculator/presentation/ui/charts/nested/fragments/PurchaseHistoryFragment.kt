package com.example.bondcalculator.presentation.ui.charts.nested.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bondcalculator.databinding.FragmentPurchaseHistoryBinding
import com.example.bondcalculator.presentation.AppApplication
import com.example.bondcalculator.presentation.ui.charts.nested.NameNestedFragment
import com.example.bondcalculator.presentation.ui.charts.nested.viewmodels.PurchaseHistoryViewModel
import com.example.bondcalculator.presentation.ui.charts.nested.viewmodels.viewmodels_factory.PurchaseHistoryViewModelFactory
import javax.inject.Inject

class PurchaseHistoryFragment : Fragment(), NameNestedFragment {

    @Inject
    lateinit var viewModelFactory: PurchaseHistoryViewModelFactory
    private val viewModel: PurchaseHistoryViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentPurchaseHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ")
        _binding = FragmentPurchaseHistoryBinding.inflate(inflater, container, false)
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

    override fun getNameFragment(): String {
        return NAME
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy:")
        super.onDestroy()
    }

    companion object {
        private const val TAG = "PurchaseHistoryFragment"
        private const val NAME = "История стоимости"
    }
}