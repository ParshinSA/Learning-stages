package com.example.weatherapplication.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherapplication.R
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.databinding.FragmentWeatherReportBinding
import com.example.weatherapplication.ui.AppApplication
import com.example.weatherapplication.ui.common.ReportPeriods
import com.example.weatherapplication.ui.viewmodels.viewmodel_classes.ReportViewModel
import com.example.weatherapplication.ui.viewmodels.viewmodel_factory.ReportViewModelFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class ReportFragment : Fragment(R.layout.fragment_weather_report) {

    @Inject
    lateinit var reportViewModelFactory: ReportViewModelFactory
    private val reportViewModel: ReportViewModel by viewModels { reportViewModelFactory }

    private val binding by viewBinding(FragmentWeatherReportBinding::bind)

    private var snackbar: Snackbar? = null
    private var dialog: AlertDialog? = null
    private val currentForecast: Forecast
        get() = requireArguments().getParcelable(DetailsForecastFragment.KEY_FORECAST)
            ?: error("$TAG No default forecast")

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
    }

    private fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        actionInFragment()
    }

    private fun actionInFragment() {
        setSelectedCityName()
        setPeriodList()
        observeData()
        generateReport()
        cancelReport()
        backButtonClickListener()
    }

    private fun observeData() {
        reportViewModel.isLoadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            changeStateComponentView(isLoading)
        }

        reportViewModel.isSaveReportLiveData.observe(viewLifecycleOwner) { loadingComplete ->
            binding.pbLoader.isVisible = false
            if (loadingComplete) showSnackBar()
        }

        reportViewModel.reportFileLiveData.observe(viewLifecycleOwner) {
            openFile(it)
        }

        reportViewModel.errorMessage.observe(viewLifecycleOwner) {
            dialog = AlertDialog.Builder(requireContext())
                .setMessage(it)
                .create()

            dialog?.show()
        }
    }

    private fun openFile(it: String) {
        dialog = AlertDialog.Builder(requireContext())
            .setTitle(this.getString(R.string.ReportFragment_dialog_text_Report))
            .setMessage(it)
            .create()
        dialog?.show()
    }

    private fun showSnackBar() {
        snackbar = Snackbar.make(
            requireView(),
            this.getString(R.string.ReportFragment_Text_Report_generated),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(this.getString(R.string.ReportFragment_Text_Report_open)) {
                openReport()
            }
        snackbar?.show()
    }

    private fun openReport() {
        reportViewModel.openReport()
        Toast.makeText(
            requireContext(),
            this.getString(R.string.ReportFragment_Text_Report_open),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun changeStateComponentView(isLoading: Boolean) {
        binding.tilContainerPeriod.isEnabled = !isLoading
        binding.btnOk.isEnabled = !isLoading
        binding.pbLoader.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun generateReport() {
        binding.btnOk.setOnClickListener {
            val userChoice = binding.tilContainerPeriod.editText?.text.toString()

            reportViewModel.generateReport(
                currentForecast,
                ReportPeriods.values().filter { it.stringQuantity == userChoice }[0]
            )
        }
    }

    private fun cancelReport() {
        binding.btnCancel.setOnClickListener {
            navigateUp(findNavController(), null)
        }
    }

    private fun setSelectedCityName() {
        binding.tvCityName.text = this.getString(
            R.string.ReportFragment_city_name_and_country,
            currentForecast.cityName,
            currentForecast.sys.country
        )
    }

    private fun setPeriodList() {
        val autoCompPeriodList = binding.tilContainerPeriod.editText as? AutoCompleteTextView
            ?: error("Incorrect autoCompPeriodList")

        autoCompPeriodList.setText(
            this.getString(R.string.ReportFragment_default_period)
        )

        autoCompPeriodList.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.item_period_list,
                ReportPeriods.values().map { it.stringQuantity }
            )
        )
    }

    private fun backButtonClickListener() {
        binding.tbReportFrg.setNavigationOnClickListener {
            navigateUp(findNavController(), null)
        }
    }

    override fun onDestroy() {
        dialog?.dismiss()
        snackbar?.dismiss()
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
    }

    companion object {
        const val TAG = "ReportFrg_Logging"
    }
}