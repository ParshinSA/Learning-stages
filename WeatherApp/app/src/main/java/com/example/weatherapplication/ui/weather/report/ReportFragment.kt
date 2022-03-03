package com.example.weatherapplication.ui.weather.report

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
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherapplication.R
import com.example.weatherapplication.data.models.forecast.Forecast
import com.example.weatherapplication.databinding.FragmentWeatherReportBinding
import com.example.weatherapplication.ui.AppApplication
import com.example.weatherapplication.ui.weather.detail_forecast.DetailsForecastFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform
import javax.inject.Inject

class ReportFragment : Fragment(R.layout.fragment_weather_report) {

    @Inject
    lateinit var reportViewModelFactory: ReportViewModelFactory
    private val reportViewModel: ReportViewModel by viewModels { reportViewModelFactory }

    private val bind by viewBinding(FragmentWeatherReportBinding::bind)

    private lateinit var fabInDetailFrg: FloatingActionButton
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
        fabInDetailFrg = requireActivity().findViewById(R.id.fab_Open_report)
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
        backButtonClickListener()
    }

    private fun observeData() {
        reportViewModel.isLoadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            changeStateComponentView(isLoading)
        }

        reportViewModel.isSaveReportLiveData.observe(viewLifecycleOwner) { loadingComplete ->
            bind.pbLoader.isVisible = false
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
        snackbar = Snackbar.make(
            requireView(),
            this.getString(R.string.SearchCityFragment_Text_Report_generated),
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(this.getString(R.string.SearchCityFragment_Text_Report_open)) {
                openReport()
            }
        snackbar?.show()
    }

    private fun openReport() {
        reportViewModel.openReport()
        Toast.makeText(
            requireContext(),
            this.getString(R.string.SearchCityFragment_Text_Report_open),
            Toast.LENGTH_LONG
        ).show()
    }

    private fun changeStateComponentView(isLoading: Boolean) {
        bind.tilContainerPeriod.isEnabled = !isLoading
        bind.btnOk.isEnabled = !isLoading
        bind.pbLoader.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun generateReport() {
        bind.btnOk.setOnClickListener {
            val userChoice = bind.tilContainerPeriod.editText?.text.toString()

            reportViewModel.generateReport(
                currentForecast,
                Period.values().filter { it.stringQuantity == userChoice }[0]
            )
        }
    }

    private fun cancelReport() {
        bind.btnCancel.setOnClickListener {
            navigateUp(findNavController(), null)
        }
    }

    private fun setSelectedCityName() {
        bind.tvCityName.text = this.getString(
            R.string.WeatherReportFragment_city_name_and_country,
            currentForecast.cityName,
            currentForecast.sys.country
        )
    }

    private fun setPeriodList() {
        val autoCompPeriodList = bind.tilContainerPeriod.editText as? AutoCompleteTextView
            ?: error("Incorrect autoCompPeriodList")

        autoCompPeriodList.setText(
            this.getString(R.string.WeatherReportFragment_default_period)
        )

        autoCompPeriodList.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.item_period_list,
                Period.values().map { it.stringQuantity }
            )
        )
    }

    private fun backButtonClickListener() {
        bind.tbReportFrg.setNavigationOnClickListener {
            navigateUp(findNavController(), null)
        }
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
            addTarget(R.id.frg_nav_host)
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