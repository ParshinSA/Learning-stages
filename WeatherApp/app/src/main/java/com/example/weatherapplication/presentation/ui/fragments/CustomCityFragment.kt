package com.example.weatherapplication.presentation.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherapplication.R
import com.example.weatherapplication.data.database.models.city.City
import com.example.weatherapplication.databinding.FragmentSearchCityBinding
import com.example.weatherapplication.presentation.ui.AppApplication
import com.example.weatherapplication.presentation.ui.common.ItemDecoration
import com.example.weatherapplication.presentation.ui.common.SearchCityAdapterRV
import com.example.weatherapplication.presentation.viewmodels.viewmodel_classes.CustomCityViewModel
import com.example.weatherapplication.presentation.viewmodels.viewmodel_factory.SearchCityViewModelFactory
import io.reactivex.Observable
import javax.inject.Inject


class CustomCityFragment : Fragment(R.layout.fragment_search_city) {
    @Inject
    lateinit var searchViewModelFactory: SearchCityViewModelFactory
    private val customViewModel: CustomCityViewModel by viewModels { searchViewModelFactory }

    private val binding by viewBinding(FragmentSearchCityBinding::bind)

    private lateinit var searchCityAdapterRV: SearchCityAdapterRV

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inject()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: ")
        actionInFragment()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun inject() {
        (requireContext().applicationContext as AppApplication).appComponent.inject(this)
    }

    private fun actionInFragment() {
        initComponentsFrg()
        backButtonClickListener()
        observeData()
    }

    private fun backButtonClickListener() {
        binding.tbSearchCity.setNavigationOnClickListener {
            navigateUp(findNavController(), null)
        }
    }

    private fun observeData() {
        customViewModel.resultCityLiveData.observe(viewLifecycleOwner) {
            binding.tvTextNoResult.isVisible = it.isEmpty()
            showResultSearchCity(it)
        }
    }

    private fun initComponentsFrg() {
        initRv()
        initSearchMenu()
    }

    private fun initSearchMenu() {
        val searchItemMenu = binding.tbSearchCity.menu.findItem(R.id.search_menu_item)
        val searchView = searchItemMenu.actionView as SearchView
        searchView.queryHint = this.getString(R.string.SearchCityFragment_Text_Enter_the_title)

        addTextChangeListener(searchView)
    }

    private fun addTextChangeListener(searchView: SearchView) {
        customViewModel.searchCity(
            Observable.create { subscriber ->
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(userInput: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(userInput: String?): Boolean {
                        userInput?.let { subscriber.onNext(userInput) }
                        return false
                    }
                })
            }
        )
    }

    private fun initRv() {
        searchCityAdapterRV = SearchCityAdapterRV { city: City ->
            addCity(city)
        }

        with(binding.rvResultSearchCity) {
            adapter = searchCityAdapterRV
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(ItemDecoration(requireContext(), 10))
            setHasFixedSize(true)
        }
    }

    private fun addCity(city: City) {
        customViewModel.addCity(city)
    }

    private fun showResultSearchCity(cityList: List<City>) {
        searchCityAdapterRV.submitList(cityList)
    }

    companion object {
        const val TAG = "SearchCityFrg_Logging"
    }
}