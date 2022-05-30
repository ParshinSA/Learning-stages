package com.example.bondcalculator.presentation.ui.charts.nested.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bondcalculator.R
import com.example.bondcalculator.databinding.FragmentTextInfoDepositBinding

class TextInfoDepositFragment : Fragment() {

    private var _binding: FragmentTextInfoDepositBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ")

        _binding = FragmentTextInfoDepositBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onPause() {
        Log.d(TAG, "onPause: ")
        super.onPause()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy:")
        super.onDestroy()
    }

    companion object {
        private const val TAG = "TextInfoDepositFragment"

    }
}

