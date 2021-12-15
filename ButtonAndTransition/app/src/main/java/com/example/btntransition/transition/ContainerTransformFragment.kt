package com.example.btntransition.transition

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.btntransition.R
import com.google.android.material.transition.MaterialContainerTransform

class ContainerTransformFragment : Fragment(R.layout.frg_container_transform) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myEnterTransition()

    }

    private fun myEnterTransition() {
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragment_nav_host
            duration = 200
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(ContextCompat.getColor(requireContext(), R.color.white))
        }
    }
}