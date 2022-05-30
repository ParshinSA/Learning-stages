package com.example.bondcalculator.presentation.ui.composition

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.bondcalculator.databinding.FragmentCompositionBinding

class CompositionFragment : Fragment() {

    private var _binding: FragmentCompositionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ")
        _binding = FragmentCompositionBinding.inflate(inflater, container, false)
        initComponents()
        return binding.root
    }

    private fun initComponents() {
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