package com.example.bondcalculator.presentation.fragments

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.bondcalculator.R

abstract class BaseFragment : Fragment() {

    protected var myDialog: AlertDialog? = null

    protected fun showDialogError(message: String) {
        myDialog = AlertDialog.Builder(requireContext())
            .setTitle(this.getString(R.string.dialog_attention))
            .setMessage(message)
            .create()
        myDialog!!.show()
    }

}