package com.example.bondcalculator.presentation.ui.charts.nested

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bondcalculator.presentation.ui.charts.nested.fragments.PayoutsFragment
import com.example.bondcalculator.presentation.ui.charts.nested.fragments.PortfolioFragment
import com.example.bondcalculator.presentation.ui.charts.nested.fragments.PurchaseHistoryFragment

class MyViewPager2Adapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    private val listFragments: List<Fragment> =
        listOf(PortfolioFragment(), PayoutsFragment(), PurchaseHistoryFragment())

    override fun getItemCount(): Int = listFragments.size

    override fun createFragment(position: Int): Fragment {
        return listFragments[position]
    }
}