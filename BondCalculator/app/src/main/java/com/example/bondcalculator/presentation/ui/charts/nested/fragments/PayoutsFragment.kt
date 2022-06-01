package com.example.bondcalculator.presentation.ui.charts.nested.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bondcalculator.databinding.FragmentPayoutsBinding
import com.example.bondcalculator.presentation.AppApplication
import com.example.bondcalculator.presentation.models.PayoutsItem
import com.example.bondcalculator.presentation.ui.charts.nested.common.NameNestedFragment
import com.example.bondcalculator.presentation.ui.charts.nested.common.PayoutsItemListAdapter
import com.example.bondcalculator.presentation.ui.charts.nested.viewmodels.PayoutsViewModel
import com.example.bondcalculator.presentation.ui.charts.nested.viewmodels.viewmodels_factory.PayoutsViewModelFactory
import javax.inject.Inject

class PayoutsFragment : Fragment(), NameNestedFragment {

    private var _binding: FragmentPayoutsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: PayoutsViewModelFactory
    private val viewModel: PayoutsViewModel by viewModels { viewModelFactory }

    private lateinit var adapterRVPayoutsItem: PayoutsItemListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPayoutsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        actionInFragment()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun actionInFragment() {
        inject()
        initRv()
        observableData()
        viewModel.getData()
    }

    private fun initRv() {
        adapterRVPayoutsItem = PayoutsItemListAdapter()
        with(binding.recyclerViewPayoutsInfoList) {
            adapter = adapterRVPayoutsItem
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observableData() {
        viewModel.dataPayoutsLiveData.observe(viewLifecycleOwner) { data ->
            setDataInRecyclerView(data)
        }
    }

    private fun setDataInRecyclerView(data: List<PayoutsItem>) {
        adapterRVPayoutsItem.submitList(data)
    }

    private fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }


    override fun getNameFragment(): String {
        return NAME
    }

    companion object {
        private const val NAME = "Выплаты"
    }
}