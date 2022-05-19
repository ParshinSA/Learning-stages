package com.example.bondcalculator.presentation.ui.charts.nested.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bondcalculator.R
import com.example.bondcalculator.databinding.FragmentTextInfoDepositBinding

class TextInfoDepositFragment : Fragment(R.layout.fragment_text_info_deposit) {

    private var _binding: FragmentTextInfoDepositBinding? = null
    private val binding: FragmentTextInfoDepositBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTextInfoDepositBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}