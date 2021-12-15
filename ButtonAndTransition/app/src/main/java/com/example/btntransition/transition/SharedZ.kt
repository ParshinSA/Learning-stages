package com.example.btntransition.transition

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.btntransition.R
import com.google.android.material.transition.MaterialSharedAxis

class SharedZ: Fragment(R.layout.frg_container_transform) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = 200
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            duration = 200
        }
    }
}