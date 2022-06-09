package com.example.bondcalculator.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bondcalculator.R
import com.example.bondcalculator.databinding.FragmentTextInfoDepositBinding
import com.example.bondcalculator.presentation.AppApplication
import com.example.bondcalculator.presentation.models.TextInfoDepositData
import com.example.bondcalculator.presentation.viewmodels.TextInfoDepositViewModel
import com.example.bondcalculator.presentation.viewmodels.viewmodels_factory.TextInfoDepositViewModelFactory
import javax.inject.Inject
import kotlin.math.roundToInt

class TextInfoDepositFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: TextInfoDepositViewModelFactory
    private val viewModel: TextInfoDepositViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentTextInfoDepositBinding? = null
    private val binding get() = _binding!!

    private var myDialog: AlertDialog? = null

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
        actionInFragment()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun actionInFragment() {
        inject()
        observeData()
        viewModel.getData()
    }

    private fun observeData() {
        viewModel.dataTextInfoDepositLiveData.observe(viewLifecycleOwner) { data ->
            setData(data)
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { message ->
            showDialogError(message)
        }
    }

    private fun setData(data: TextInfoDepositData) {
        binding.textViewValueInvestmentToday.text = String.format("%,.0f", data.startBalance)

        binding.textViewTxtValYearYield.text =
            resources.getString(R.string.textViewTxtValYearYield, data.resultYield.roundToInt())

        binding.textViewValueAfter.text =
            resources.getQuantityString(R.plurals.valueInvestmentTerm, data.term, data.term)

        binding.textViewValueWillBe.text = String.format("%,.0f", data.endBalance)

        binding.textViewValueProfit.text = String.format("%,.0f", data.profit)
    }

    private fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    private fun showDialogError(message: String) {
        myDialog = AlertDialog.Builder(requireContext())
            .setTitle(this.getString(R.string.dialog_attention))
            .setMessage(message)
            .create()
        myDialog!!.show()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy:")
        super.onDestroy()
    }

    companion object {
        private const val TAG = "TextInfoDepositFragment"
    }
}

