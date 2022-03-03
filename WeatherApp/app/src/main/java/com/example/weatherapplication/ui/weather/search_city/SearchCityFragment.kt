package com.example.weatherapplication.ui.weather.search_city

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.weatherapplication.R
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.objects.CustomCities
import com.example.weatherapplication.databinding.FragmentSearchCityBinding
import com.example.weatherapplication.ui.AppApplication
import com.example.weatherapplication.utils.ItemDecoration
import io.reactivex.Observable
import javax.inject.Inject


class SearchCityFragment : Fragment(R.layout.fragment_search_city) {

    @Inject
    lateinit var customCities: CustomCities

    @Inject
    lateinit var searchViewModelFactory: SearchCityViewModelFactory
    private val searchViewModel: SearchCityViewModel by viewModels { searchViewModelFactory }

    private val bind by viewBinding(FragmentSearchCityBinding::bind)

    private lateinit var resultSearchAdapter: ResultSearchAdapter

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
        bind.tbSearchCity.setNavigationOnClickListener {
            navigateUp(findNavController(), null)
        }
    }

    private fun observeData() {
        searchViewModel.resultCityLiveData.observe(viewLifecycleOwner) {
            bind.tvTextNoResult.isVisible = it.isEmpty()
            showResultSearchCity(it)
        }
    }

    private fun initComponentsFrg() {
        initRv()
        initSearchMenu()
    }

    private fun initSearchMenu() {
        val searchItemMenu = bind.tbSearchCity.menu.findItem(R.id.search_menu_item)
        val searchView = searchItemMenu.actionView as SearchView
        searchView.queryHint = this.getString(R.string.SearchCityFragment_Text_Enter_the_title)

        addTextChangeListener(searchView)
    }

    private fun addTextChangeListener(searchView: SearchView) {
        searchViewModel.textInputProcessing(
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
        resultSearchAdapter = ResultSearchAdapter { city: City ->
            customCities.addCity(city)
        }

        with(bind.rvResultSearchCity) {
            adapter = resultSearchAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(ItemDecoration(requireContext(), 10))
            setHasFixedSize(true)
        }
    }

    private fun showResultSearchCity(cityList: List<City>) {
        resultSearchAdapter.submitList(cityList)
    }

    companion object {
        const val TAG = "SearchCityFrg_Logging"
    }
}