package com.example.bondcalculator.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bondcalculator.R
import com.example.bondcalculator.databinding.FragmentPurchaseHistoryBinding
import com.example.bondcalculator.presentation.AppApplication
import com.example.bondcalculator.presentation.common.NameNestedFragment
import com.example.bondcalculator.presentation.common.PurchaseHistoryItemListAdapterRv
import com.example.bondcalculator.presentation.models.PurchaseHistoryItemRv
import com.example.bondcalculator.presentation.viewmodels.PurchaseHistoryViewModel
import com.example.bondcalculator.presentation.viewmodels.viewmodels_factory.PurchaseHistoryViewModelFactory
import javax.inject.Inject

class PurchaseHistoryFragment : BaseFragment(), NameNestedFragment {

    @Inject
    lateinit var viewModelFactory: PurchaseHistoryViewModelFactory
    private val viewModel: PurchaseHistoryViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentPurchaseHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var purchaseHistoryItemListAdapter: PurchaseHistoryItemListAdapterRv

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPurchaseHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        actionInFragment()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun actionInFragment() {
        inject()
        initRecyclerView()
        observeData()
        viewModel.getData()
    }

    private fun initRecyclerView() {
        purchaseHistoryItemListAdapter = PurchaseHistoryItemListAdapterRv()
        with(binding.recyclerViewPurchaseHistoryInfoList) {
            adapter = purchaseHistoryItemListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observeData() {
        viewModel.dataPurchaseHistoryLiveData.observe(viewLifecycleOwner) { data ->
            setDataInRecyclerView(data)
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { message ->
            showDialogError(message)
        }
    }

    private fun setDataInRecyclerView(data: List<PurchaseHistoryItemRv>) {
        purchaseHistoryItemListAdapter.submitList(data)
    }

    private fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    override fun getNameFragment(): String {
        return NAME
    }

    companion object {
        private const val NAME = "История стоимости"
    }
}