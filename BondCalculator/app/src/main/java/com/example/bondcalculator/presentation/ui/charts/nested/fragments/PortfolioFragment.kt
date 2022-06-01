package com.example.bondcalculator.presentation.ui.charts.nested.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bondcalculator.R
import com.example.bondcalculator.databinding.FragmentPortfolioBinding
import com.example.bondcalculator.domain.models.analysis_portfolio_yield.DomainDataForPortfolioFrg
import com.example.bondcalculator.presentation.AppApplication
import com.example.bondcalculator.presentation.ui.charts.nested.common.NameNestedFragment
import com.example.bondcalculator.presentation.ui.charts.nested.viewmodels.PortfolioViewModel
import com.example.bondcalculator.presentation.ui.charts.nested.viewmodels.viewmodels_factory.PortfolioViewModelFactory
import javax.inject.Inject

class PortfolioFragment : Fragment(), NameNestedFragment {

    @Inject
    lateinit var viewModelFactory: PortfolioViewModelFactory
    private val viewModel: PortfolioViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentPortfolioBinding? = null
    private val binding get() = _binding!!

    private var myDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPortfolioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        actionInFragment()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun actionInFragment() {
        inject()
        observableData()
        viewModel.getData()
    }

    private fun observableData() {

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) { message ->
            showDialogError(message)
        }

        viewModel.dataLiveData.observe(viewLifecycleOwner) { data ->
            settingsView(data)
        }
    }

    private fun settingsView(data: DomainDataForPortfolioFrg) {
        val weightSum = data.counterBonds.values.sum()
        binding.linearLayoutContainerView.weightSum = weightSum.toFloat()

        for ((shortName, counter) in data.counterBonds) {
            createView(counter, shortName)
        }
    }

    private fun createView(weight: Int, shortName: String) {
        val newColor = viewModel.getColor()

        val newView = View(requireContext()).apply {
            setBackgroundColor(newColor)

            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, weight.toFloat()
            )
        }

        val newTextView = TextView(requireContext()).apply {
            text = shortName
            setTextColor(newColor)

            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
            )
        }

        binding.linearLayoutContainerName.addView(newTextView)
        binding.linearLayoutContainerView.addView(newView)

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
        binding.linearLayoutContainerName.removeAllViews()
        binding.linearLayoutContainerView.removeAllViews()

        _binding = null
        myDialog = null
        super.onDestroy()
    }

    override fun getNameFragment(): String {
        return NAME
    }

    companion object {
        private const val NAME = "Портфель"
    }

}