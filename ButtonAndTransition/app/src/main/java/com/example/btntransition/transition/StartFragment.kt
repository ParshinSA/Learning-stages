package com.example.btntransition.transition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.btntransition.R
import com.example.btntransition.databinding.FrgStartBinding
import com.google.android.material.transition.MaterialElevationScale

class StartFragment : Fragment(R.layout.frg_start) {

    private var _bind: FrgStartBinding? = null
    val bind: FrgStartBinding
        get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bind = FrgStartBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionInFragment()
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

    }

    private fun actionInFragment() {
        navigate()
        thisFragmentTransform()
    }

    private fun navigate() {
        bind.containerTransformMCV.setOnClickListener {
            toContainerTransform(bind.containerTransformMCV)
        }

        bind.containerTransformBtn.setOnClickListener {
            toContainerTransform2()
        }

        bind.sharedZBtn.setOnClickListener {
            toSharedZ()
        }
    }

    private fun toSharedZ() {
        findNavController().navigate(StartFragmentDirections.actionStartFragmentToSharedZ())
    }

    private fun toContainerTransform2() {
        findNavController().navigate(StartFragmentDirections.actionStartFragmentToContainerTransformFragment2())
    }

    private fun thisFragmentTransform() {
        exitTransition = MaterialElevationScale(false).apply {
            duration = 200
        }
        reenterTransition = MaterialElevationScale(true).apply {
            duration = 200
        }
    }

    private fun toContainerTransform(cardView: View) {
        val transitionName = resources.getString(R.string.trans_name_container_transform)
        val extras = FragmentNavigatorExtras(cardView to transitionName)
        val directions = StartFragmentDirections.actionStartFragmentToContainerTransformFragment()
        findNavController().navigate(directions, extras)
    }

}