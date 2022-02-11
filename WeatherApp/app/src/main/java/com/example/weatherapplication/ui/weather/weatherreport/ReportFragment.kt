package com.example.weatherapplication.ui.weather.weatherreport

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.transition.Slide
import com.example.weatherapplication.R
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.databinding.FragmentWeatherReportBinding
import com.example.weatherapplication.ui.weather.detailsforecast.DetailsForecastFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform

class ReportFragment : Fragment(R.layout.fragment_weather_report) {

    private var _bind: FragmentWeatherReportBinding? = null
    private val bind: FragmentWeatherReportBinding
        get() = _bind!!

    private lateinit var fabInDetailFrg: FloatingActionButton
    private var dialog: AlertDialog? = null
    private val reportViewModel: ReportViewModel by viewModels()

    private val selectPeriod: String
        get() = bind.periodList.editText?.text.toString()

    private val currentForecast: Forecast
        get() = requireArguments().getParcelable(DetailsForecastFragment.KEY_FORECAST)
            ?: error("$TAG No default forecast")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentWeatherReportBinding.inflate(inflater, container, false)
        fabInDetailFrg = requireActivity().findViewById(R.id.open_report_fab)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        enterReturnAnimation()
        actionInFragment()
    }

    private fun actionInFragment() {
        setSelectedCityName()
        setPeriodList()
        observeData()
        generateReport()
        cancelReport()
    }

    private fun observeData() {
        reportViewModel.isLoadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            changeStateComponentView(isLoading)
        }

        reportViewModel.isSaveReportLiveData.observe(viewLifecycleOwner) { loadingComplete ->
            bind.progressbar.isVisible = false
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
            .setTitle("Отчет")
            .setMessage(it)
            .create()
        dialog?.show()
    }

    private fun showSnackBar() {
        Snackbar.make(requireView(), "Отчет сформирован", Snackbar.LENGTH_INDEFINITE)
            .setAction("Открыть") {
                openReport()
            }
            .show()
    }

    private fun openReport() {
        reportViewModel.openReport()
        Toast.makeText(requireContext(), "Файл открыт", Toast.LENGTH_SHORT).show()
    }

    private fun changeStateComponentView(isLoading: Boolean) {
        bind.periodList.isEnabled = !isLoading
        bind.reportOkBtn.isEnabled = !isLoading
        bind.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun generateReport() {
        bind.reportOkBtn.setOnClickListener {
            reportViewModel.generateReport(currentForecast, selectPeriod)
        }
    }

    private fun cancelReport() {
        bind.reportCancelBtn.setOnClickListener {
            navigateUp(findNavController(), null)
        }
    }

    private fun setSelectedCityName() {
        bind.cityName.text = this.getString(
            R.string.WeatherReportFragment_city_name_and_country,
            currentForecast.cityName,
            currentForecast.sys.country
        )
    }

    private fun setPeriodList() {
        val autoCompPeriodList = bind.periodList.editText as? AutoCompleteTextView
            ?: error("Incorrect autoCompPeriodList")

        autoCompPeriodList.setText(
            this.getString(R.string.WeatherReportFragment_default_period)
        )

        autoCompPeriodList.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.item_period_list,
                resources.getStringArray(R.array.WeatherReportFragment_period_list).toList()
            )
        )
    }

    private fun enterReturnAnimation() {
        enterTransition = MaterialContainerTransform().apply {
            startView = fabInDetailFrg
            endView = bind.report
            duration = 300
            scrimColor = Color.TRANSPARENT
            containerColor = requireContext().getColor(R.color.white)
            startContainerColor = requireContext().getColor(R.color.white)
            endContainerColor = requireContext().getColor(R.color.white)
        }
        returnTransition = Slide().apply {
            duration = 300
            addTarget(R.id.my_nav_host_fragment)
        }
    }

    override fun onDestroy() {
        dialog?.dismiss()
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
    }

    companion object {
        const val TAG = "ReportFrg_Logging"
    }
}