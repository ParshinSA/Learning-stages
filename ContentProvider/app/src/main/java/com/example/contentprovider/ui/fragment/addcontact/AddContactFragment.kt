package com.example.contentprovider.ui.fragment.addcontact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.contentprovider.R
import com.example.contentprovider.databinding.FrgAddContactBinding

class AddContactFragment : Fragment(R.layout.frg_add_contact) {

    private var _bind: FrgAddContactBinding? = null
    private val bind: FrgAddContactBinding
        get() = _bind!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FrgAddContactBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        action()
    }

    private fun action() {

    }
}