package com.example.bondcalculator.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bondcalculator.databinding.FragmentCompositionBinding
import com.example.bondcalculator.domain.instruction.aplication_counter.ApplicationCounter
import com.example.bondcalculator.presentation.AppApplication
import com.example.bondcalculator.presentation.common.CompositionFrgAdapterRv
import com.example.bondcalculator.presentation.models.CompositionFrgItemRv
import com.example.bondcalculator.presentation.viewmodels.CompositionViewModel
import com.example.bondcalculator.presentation.viewmodels.viewmodels_factory.CompositionViewModelFactory
import javax.inject.Inject

class CompositionFragment : BaseFragment() {

    @Inject
    lateinit var viewFactory: CompositionViewModelFactory
    private val viewModel: CompositionViewModel by viewModels { viewFactory }

    @Inject
    lateinit var applicationCounter: ApplicationCounter


    private var _binding: FragmentCompositionBinding? = null
    private val binding get() = _binding!!

    private lateinit var compositionFrgAdapterRv: CompositionFrgAdapterRv

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ")
        _binding = FragmentCompositionBinding.inflate(inflater, container, false)
        actionFragment()
        return binding.root
    }

    private fun actionFragment() {
        inject()
        initRecyclerView()
        observeData()
        viewModel.getData()
        applicationCounter()
    }

    private fun initRecyclerView() {
        compositionFrgAdapterRv = CompositionFrgAdapterRv()
        with(binding.recyclerViewCompositionBondInfo) {
            adapter = compositionFrgAdapterRv
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observeData() {
        viewModel.dataCompositionFrgLiveData.observe(viewLifecycleOwner) { data ->
            setDataInRecyclerView(data)
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { message ->
            showDialogError(message)
        }
    }

    private fun applicationCounter() {
        binding.buttonSubmitApplication.setOnClickListener {
            applicationCounter.incrementCounter()
        }
    }

    private fun setDataInRecyclerView(data: List<CompositionFrgItemRv>?) {
        data?.let { compositionFrgAdapterRv.submitList(it) }
    }

    private fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        _binding = null
        super.onDestroy()
    }

    companion object {
        private const val TAG = "CompositionFragment"
    }
}