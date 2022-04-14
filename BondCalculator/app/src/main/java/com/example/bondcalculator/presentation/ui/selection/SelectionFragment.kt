package com.example.bondcalculator.presentation.ui.selection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bondcalculator.R
import com.example.bondcalculator.databinding.FragmentSelectionBinding
import com.example.bondcalculator.presentation.AppApplication
import com.example.bondcalculator.presentation.viewmodels.SelectionViewModel
import com.example.bondcalculator.presentation.viewmodels.factory.SelectionViewModelFactory
import javax.inject.Inject

class SelectionFragment : Fragment(R.layout.fragment_selection) {

    @Inject
    lateinit var viewModelFactory: SelectionViewModelFactory
    private val viewModel: SelectionViewModel by viewModels { viewModelFactory }

    private val binding by viewBinding(FragmentSelectionBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initComponents()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun initComponents() {
        inject()
        getData()
    }

    private fun getData() {
        viewModel.getData()
    }

    private fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    companion object {
        private val TAG = this::class.qualifiedName
    }
}