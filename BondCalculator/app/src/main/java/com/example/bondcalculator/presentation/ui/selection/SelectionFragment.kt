package com.example.bondcalculator.presentation.ui.selection

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bondcalculator.R
import com.example.bondcalculator.common.DEFAULT_INVESTMENT_AMOUNT_SEEKBAR
import com.example.bondcalculator.common.DEFAULT_INVESTMENT_TERM_SEEKBAR
import com.example.bondcalculator.common.DEFAULT_MIN_INVESTMENT_AMOUNT_SEEKBAR
import com.example.bondcalculator.common.DEFAULT_REPLENISHMENT_MONTH_AMOUNT
import com.example.bondcalculator.databinding.FragmentSelectionBinding
import com.example.bondcalculator.presentation.AppApplication
import com.example.bondcalculator.presentation.viewmodels.SelectionViewModel
import com.example.bondcalculator.presentation.viewmodels.factory.SelectionViewModelFactory
import javax.inject.Inject

class SelectionFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: SelectionViewModelFactory
    private val viewModel: SelectionViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentSelectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSelectionBinding.inflate(inflater, container, false)
        savedInstanceState ?: setDefaultValues()
        actionFragment()
        return binding.root
    }

    private fun setDefaultValues() {
        binding.seekBarInvestmentTerm.progress = DEFAULT_INVESTMENT_TERM_SEEKBAR
        binding.seekBarInvestmentAmount.progress = DEFAULT_INVESTMENT_AMOUNT_SEEKBAR
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.seekBarInvestmentAmount.min = DEFAULT_MIN_INVESTMENT_AMOUNT_SEEKBAR
        }
        binding.textViewTxtAmountRegularReplenishment.text =
            resources.getString(
                R.string.textViewTxtAmountRegularReplenishment,
                DEFAULT_REPLENISHMENT_MONTH_AMOUNT.toInt()
            )
    }

    private fun actionFragment() {
        inject()
        observeData()
        investmentTermListener()
        investmentAmountListener()
        selectedInvestmentAccountListener()
        selectedInvestmentCurrencyListener()
        isReplenishBalanceListener()

        collectPortfolio()
    }

    private fun isReplenishBalanceListener() {
        binding.checkBoxRegularReplenishment.setOnCheckedChangeListener { _, state ->
            viewModel.isReplenishBalance(state)
        }
    }

    private fun selectedInvestmentCurrencyListener() {
        buttonListener(binding.buttonInvestmentCurrencyRUB)
        buttonListener(binding.buttonInvestmentCurrencyUSD)
    }

    private fun selectedInvestmentAccountListener() {
        buttonListener(binding.buttonInvestmentAccountNormal)
        buttonListener(binding.buttonInvestmentAccountIIS)
    }

    private fun investmentAmountListener() {
        seekBarListener(binding.seekBarInvestmentAmount) { seekBar: SeekBar ->
            viewModel.setValueInvestmentAmount(seekBar.progress)
        }
    }

    private fun investmentTermListener() {
        seekBarListener(binding.seekBarInvestmentTerm) { seekBar: SeekBar ->
            viewModel.setValueInvestmentTerm(seekBar.progress)
        }
    }

    private fun collectPortfolio() {
        binding.buttonCollectPortfolio.setOnClickListener {
            viewModel.collectPortfolio()
        }
    }

    private fun buttonListener(button: Button) {
        button.setOnClickListener {
            viewModel.clickButton(button)
        }
    }

    private fun observeData() {
        viewModel.investmentTermValueLiveDta.observe(viewLifecycleOwner) { value ->
            setValueInvestmentTerm(value)
        }

        viewModel.investmentAmountValueLiveDta.observe(viewLifecycleOwner) { value ->
            setValueInvestmentAmount(value)
        }

        viewModel.colorButtonRubLiveData.observe(viewLifecycleOwner) { colorId ->
            binding.buttonInvestmentCurrencyRUB.setBackgroundColor(
                resources.getColor(colorId, null)
            )
        }

        viewModel.colorButtonUsdLiveData.observe(viewLifecycleOwner) { colorId ->
            binding.buttonInvestmentCurrencyUSD.setBackgroundColor(
                resources.getColor(colorId, null)
            )
        }

        viewModel.colorButtonIisLiveData.observe(viewLifecycleOwner) { colorId ->
            binding.buttonInvestmentAccountIIS.setBackgroundColor(
                resources.getColor(colorId, null)
            )
        }

        viewModel.colorButtonNormalLiveData.observe(viewLifecycleOwner) { colorId ->
            binding.buttonInvestmentAccountNormal.setBackgroundColor(
                resources.getColor(colorId, null)
            )
        }

    }

    private fun seekBarListener(seekBar: SeekBar, actionSeekbar: (SeekBar) -> Unit) {
        seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, p1: Int, p2: Boolean) {
                seekBar?.let { actionSeekbar(it) }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
    }

    private fun setValueInvestmentTerm(value: Int) {
        binding.textViewValueInvestmentTerm.text =
            resources.getQuantityString(R.plurals.valueInvestmentTerm, value, value)
    }

    private fun setValueInvestmentAmount(value: Int) {
        binding.textViewValueInvestmentAmount.text =
            resources.getString(R.string.valueInvestmentAmount, value)
    }

    private fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    companion object {
        private val TAG = this::class.qualifiedName
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
