package com.example.weatherapplication.ui.weather.searchcity

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
import com.example.weatherapplication.R
import com.example.weatherapplication.data.models.city.City
import com.example.weatherapplication.data.objects.AppDisposable
import com.example.weatherapplication.data.objects.CustomCities
import com.example.weatherapplication.databinding.FragmentSearchCityBinding
import com.example.weatherapplication.ui.weather.searchcity.recyclerview.ResultSearchAdapter
import com.example.weatherapplication.ui.weather.shortforecastlist.recyclerview.ItemDecoration
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


class SearchCityFragment : Fragment(R.layout.fragment_search_city) {

    private var _bind: FragmentSearchCityBinding? = null
    private val bind: FragmentSearchCityBinding
        get() = _bind!!

    private lateinit var resultSearchAdapter: ResultSearchAdapter
    private val viewModel: SearchCityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "onCreateView: ")
        _bind = FragmentSearchCityBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: ")
        actionInFragment()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun actionInFragment() {
        initComponentsFrg()
        backButtonClickListener()
        observeData()
    }

    private fun backButtonClickListener() {
        bind.toolbarSearchCity.setNavigationOnClickListener {
            navigateUp(findNavController(), null)
        }
    }

    private fun observeData() {
        viewModel.resultCityLiveData.observe(viewLifecycleOwner) {
            bind.noResult.isVisible = it.isEmpty()
            showResultSearchCity(it)
        }
    }

    private fun initComponentsFrg() {
        initRv()
        initSearchMenu()
    }

    private fun initSearchMenu() {
        val searchItemMenu = bind.toolbarSearchCity.menu.findItem(R.id.search_menu_item)
        val searchView = searchItemMenu.actionView as SearchView
        searchView.queryHint = "Введите название"

        addTextChangeListener(searchView)
    }

    private fun addTextChangeListener(searchView: SearchView) {
        AppDisposable.disposableList.add(
            subscribeTextListener(searchView)
                .filter { it.length >= 3 }
                .debounce(250, TimeUnit.MILLISECONDS)
                .distinctUntilChanged()
                .subscribe {
                    Log.d(TAG, "addTextChangeListener: search start: $it ")
                    viewModel.searchCity(it)
                }
        )
    }

    private fun subscribeTextListener(searchView: SearchView): Observable<String> {
        return Observable.create { subscriber ->
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    p0?.let { subscriber.onNext(p0) }
                    return false
                }
            })
        }
    }

    private fun initRv() {
        resultSearchAdapter = ResultSearchAdapter { city: City ->
            CustomCities.addCity(city)
        }

        with(bind.resultSearchCity) {
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