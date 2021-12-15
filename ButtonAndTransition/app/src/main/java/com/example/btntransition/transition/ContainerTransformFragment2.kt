package com.example.btntransition.transition

import android.graphics.Color
import android.os.Bundle
import androidx.transition.Slide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.btntransition.R
import com.example.btntransition.databinding.FrgContainerTransformBinding
import com.google.android.material.transition.MaterialContainerTransform

class ContainerTransformFragment2 : Fragment(R.layout.frg_container_transform2) {

    private var _bind: FrgContainerTransformBinding? = null
    val bind: FrgContainerTransformBinding
        get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bind = FrgContainerTransformBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enterTransition = MaterialContainerTransform().apply {
            startView = requireActivity().findViewById(R.id.container_transform_btn)
            endView = bind.imageCardView
            duration = 200
            scrimColor = Color.TRANSPARENT
            containerColor = ContextCompat.getColor(requireContext(), R.color.black)
            startContainerColor = ContextCompat.getColor(requireContext(), R.color.purple_200)
            endContainerColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
        returnTransition = Slide().apply {
            duration = 200
            addTarget(R.id.container_transform_btn)
        }
    }

}