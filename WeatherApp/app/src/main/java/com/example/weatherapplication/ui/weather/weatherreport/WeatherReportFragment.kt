package com.example.weatherapplication.ui.weather.weatherreport

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.transition.Slide
import com.example.weatherapplication.DefaultListCity
import com.example.weatherapplication.R
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.databinding.FragmentWeatherReportBinding
import com.example.weatherapplication.ui.AppActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialContainerTransform

class WeatherReportFragment : Fragment() {

    private var _bind: FragmentWeatherReportBinding? = null
    private val bind: FragmentWeatherReportBinding
        get() = _bind!!

    private lateinit var fabInActivity: FloatingActionButton

    private val reportViewModel: ReportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bind = FragmentWeatherReportBinding.inflate(inflater, container, false)
        fabInActivity = requireActivity().findViewById(R.id.open_report_fab)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enterReturnAnimation()
        actionInFragment()
    }

    private fun actionInFragment() {
        setCityList()
        setPeriodList()
        cancelReport()
        generateReport()
        observe()
    }

    private fun observe() {
        reportViewModel.isLoadingLiveData.observe(viewLifecycleOwner) { isLoading ->
            changeStateComponentView(isLoading)
        }

        reportViewModel.loadingComplete.observe(viewLifecycleOwner) { loadingComplete ->
            if (loadingComplete) showSnackbar()
        }

        reportViewModel.reportFileLiveData.observe(viewLifecycleOwner) {
            openFile(it)
        }
    }

    private fun openFile(it: String) {
       AlertDialog.Builder(requireContext())
           .setTitle("Отчет")
           .setMessage(it)
           .create()
           .show()
    }

    private fun showSnackbar() {
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
        bind.cityList.isEnabled = !isLoading
        bind.periodList.isEnabled = !isLoading
        bind.reportOkBtn.isEnabled = !isLoading
        bind.progressbar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun checkingNameCityAndPeriod(): Boolean {
        val nameCity = bind.cityList.editText?.text.toString()
        val period = bind.periodList.editText?.text.toString()

        if (nameCity.isNotEmpty()) bind.cityList.error = null
        else bind.cityList.error = "Выберите город"

        if (period.isNotEmpty()) bind.periodList.error = null
        else bind.periodList.error = "Выберите период"

        return nameCity.isNotEmpty() && period.isNotEmpty()
    }

    private fun generateReport() {
        bind.reportOkBtn.setOnClickListener {
            val nameCity = bind.cityList.editText?.text.toString()
            val period = bind.periodList.editText?.text.toString()
            if (checkingNameCityAndPeriod()) {
                val idCity = DefaultListCity.listCity.filter { it.name == nameCity }[0].id
                reportViewModel.generateReport(nameCity, idCity, period)
            }
        }
    }

    private fun cancelReport() {
        bind.reportCancelBtn.setOnClickListener {
            navigateUp(findNavController(), null)
        }
    }

    private fun setCityList() {
        val autoCompleteCityList = bind.cityList.editText as? AutoCompleteTextView
            ?: error("Incorrect autoCompCityList")

        val cityList = DefaultListCity.listCity.map(City::name).sorted()

        val adapterAutoComplete =
            ArrayAdapter(requireContext(), R.layout.item_city_list, cityList)

        autoCompleteCityList.setAdapter(adapterAutoComplete)

        val defaultNameCity: String? =
            if (requireArguments().getInt(AppActivity.KEY_ID_DIRECTION)
                == R.id.action_detailsForecastFragment_to_weatherReportFragment
            ) requireArguments().getString(AppActivity.KEY_CITY_NAME) else null

        defaultNameCity?.let { autoCompleteCityList.setText(it, false) }
        addTextChangedListener(autoCompleteCityList)
    }

    private fun setPeriodList() {
        val autoCompPeriodList = bind.periodList.editText as? AutoCompleteTextView
            ?: error("Incorrect autoCompPeriodList")

        val periodList =
            resources.getStringArray(R.array.WeatherReportFragment_period_list).toList()

        val adapter = ArrayAdapter(requireContext(), R.layout.item_period_list, periodList)

        autoCompPeriodList.setAdapter(adapter)
        addTextChangedListener(autoCompPeriodList)
    }

    private fun addTextChangedListener(autoComp: AutoCompleteTextView) {
        autoComp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                checkingNameCityAndPeriod()
            }
        })
    }

    private fun enterReturnAnimation() {
        enterTransition = MaterialContainerTransform().apply {
            startView = fabInActivity
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
        fabInActivity.visibility = View.VISIBLE
        super.onDestroy()
    }
}