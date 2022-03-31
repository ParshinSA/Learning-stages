package com.example.weatherapplication.presentation.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherapplication.R
import com.example.weatherapplication.databinding.FragmentShortForecastListBinding
import com.example.weatherapplication.presentation.models.forecast.UiShortForecast
import com.example.weatherapplication.presentation.ui.AppApplication
import com.example.weatherapplication.presentation.ui.common.ItemDecoration
import com.example.weatherapplication.presentation.ui.common.ShortForecastListAdapter
import com.example.weatherapplication.presentation.viewmodels.viewmodel_classes.ShortForecastViewModel
import com.example.weatherapplication.presentation.viewmodels.viewmodel_factory.ShortForecastViewModelFactory
import javax.inject.Inject

class ShortForecastFragment : Fragment(R.layout.fragment_short_forecast_list) {

    @Inject
    lateinit var shortForecastViewModelFactory: ShortForecastViewModelFactory
    private val shortViewModel: ShortForecastViewModel by viewModels { shortForecastViewModelFactory }

    private val binding by viewBinding(FragmentShortForecastListBinding::bind)
    private lateinit var adapterRVShort: ShortForecastListAdapter
    private var myDialog: AlertDialog? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: ")
        actionInFragment()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun actionInFragment() {
        initComponents()
        addNewCity()
        updateForecastsSwipe()
        observeData()
        getForecastList()
    }

    private fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    private fun initComponents() {
        initRV()
    }

    private fun addNewCity() {
        binding.fabAddCity.setOnClickListener {
            findNavController().navigate(R.id.action_shortForecastListFragment_to_addCityFragment)
        }
    }

    private fun setLastTimeUpdateForecast() {
        binding.tvLastUpdateTime.text = this.getString(
            R.string.ShortForecastFragment_updateText_text,
            shortViewModel.getLastUpdateTimeString()
        )
    }

    private fun isConnect(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) != null
    }

    private fun changeStateInfoView(state: Boolean) {
        binding.tvTextHeader.text =
            if (!state)
                this.getString(R.string.ShortForecastFragment_text_No_city_in_list)
            else this.getString(R.string.ShortForecastFragment_text_Click_for_detailed_forecast)
        binding.tvLastUpdateTime.isVisible = state
    }

    private fun getForecastList() {
        if (isConnect()) {
            shortViewModel.getForecastList()
        } else {
            shortViewModel.errorMessage(this.getString(R.string.ShortForecastFragment_check_internet))
            shortViewModel.isLoadingMutableLiveData.postValue(false)
        }
    }

    private fun changeStateProgressView(isLoading: Boolean) {
        binding.swlSwipeContainer.isRefreshing = isLoading
    }

    private fun updateForecastsSwipe() {
        binding.swlSwipeContainer.setOnRefreshListener {
            Log.d(TAG, "swipe: start")
            getForecastList()
        }
    }

    private fun observeData() {
        shortViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { message ->
            showDialogError(message)
        }

        shortViewModel.isLoadingMutableLiveData.observe(viewLifecycleOwner) { isUpdate ->
            changeStateProgressView(isUpdate)
        }

        shortViewModel.forecastListLiveData.observe(viewLifecycleOwner) { listForecast ->
            Log.d(TAG, "observeData: forecastListLiveData $listForecast")
            updateForecastListInRV(listForecast)
        }
    }

    private fun updateForecastListInRV(listForecast: List<UiShortForecast>) {
        if (listForecast.isEmpty())
            changeStateInfoView(false)
        else {
            adapterRVShort.submitList(listForecast.sortedBy { it.cityName })
            changeStateInfoView(true)
            setLastTimeUpdateForecast()
        }
    }

    private fun showDialogError(message: String) {
        myDialog = AlertDialog.Builder(requireContext())
            .setTitle(this.getString(R.string.ShortForecastFragment_attention))
            .setMessage(message)
            .create()

        myDialog!!.show()
    }

    private fun initRV() {
        adapterRVShort =
            ShortForecastListAdapter { coordinationCity: Pair<Double, Double>, currentView: View ->
                transitionInDetailsFragment(coordinationCity, currentView)
            }

        with(binding.rvListCity) {
            adapter = adapterRVShort
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(ItemDecoration(requireContext(), 10))
            setHasFixedSize(true)
        }
    }

    private fun transitionInDetailsFragment(
        coordinationCity: Pair<Double, Double>,
        currentView: View
    ) {

        val detailsForecast = shortViewModel.getDetailForecast(coordinationCity)
        Log.d(TAG, "transitionInDetailsFragment: $detailsForecast")
        val bundle = Bundle()
        bundle.putParcelable(KEY_FORECAST, detailsForecast)

        val transitionName =
            this.resources.getString(R.string.DetailsForecastFragment_transition_name)
        val extras = FragmentNavigatorExtras(currentView to transitionName)

        findNavController().navigate(
            R.id.action_shortForecastListFragment_to_detailsForecastFragment, bundle, null, extras
        )
    }

    override fun onPause() {
        Log.d(TAG, "onPause: ")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "onStop: ")
        super.onStop()
    }

    override fun onDestroy() {
        myDialog?.dismiss()
        Log.d(TAG, "onDestroy:")
        super.onDestroy()
    }

    companion object {
        const val KEY_FORECAST = "key forecast"
        const val TAG = "ShortFrg_Logging"
    }
}