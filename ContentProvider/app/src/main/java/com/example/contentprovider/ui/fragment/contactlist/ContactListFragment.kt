package com.example.contentprovider.ui.fragment.contactlist

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contentprovider.R
import com.example.contentprovider.databinding.FrgContactListBinding
import com.example.contentprovider.ui.fragment.contactlist.adapter.ContactListAdapter
import com.example.contentprovider.utils.toast
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.ktx.constructPermissionsRequest

class ContactListFragment : Fragment(R.layout.frg_contact_list) {

    private var _binding: FrgContactListBinding? = null
    private val bind: FrgContactListBinding
        get() = _binding!!

    private lateinit var adapterRV: ContactListAdapter

    private val viewModel: ContactListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FrgContactListBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        actionInFrg()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun actionInFrg() {
        checkPermission()
        initContactListRV()
        addContact()
        observeData()
    }

    private fun checkPermission() {
        Handler(Looper.getMainLooper()).post {
            constructPermissionsRequest(
                Manifest.permission.READ_CONTACTS,
                onShowRationale = ::onContactPermissionShowRationale,
                onPermissionDenied = ::onContactPermissionDenied,
                onNeverAskAgain = ::onContactPermissionNeverAskAgain,
                requiresPermission = { viewModel.loadList() }
            )
                .launch()
        }
    }

    private fun observeData() {
        viewModel.contactsLiveData.observe(viewLifecycleOwner) { adapterRV.items = it }
        viewModel.callLiveData.observe(viewLifecycleOwner, ::callToPhone)
    }

    private fun callToPhone(phone: String) {
        Intent(Intent.ACTION_DIAL)
            .apply { data = Uri.parse("tel:$phone") }
            .also { startActivity(it) }
    }

    private fun initContactListRV() {
        adapterRV = ContactListAdapter{ contact ->
            viewModel.callToContact(contact)
        }

        with(bind.contactListRV) {
            adapter = adapterRV
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun addContact() {
        bind.addContactFab.setOnClickListener {
            findNavController().navigate(R.id.action_contactListFragment_to_addContactFragment)
        }
    }

    private fun onContactPermissionDenied() {
        toast(R.string.contact_list_permission_denied)
    }

    private fun onContactPermissionShowRationale(request: PermissionRequest) {
        request.proceed()
    }

    private fun onContactPermissionNeverAskAgain() {
        toast(R.string.contact_list_permission_never_ask_again)
    }
}