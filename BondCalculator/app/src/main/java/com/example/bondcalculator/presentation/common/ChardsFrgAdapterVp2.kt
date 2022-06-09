package com.example.bondcalculator.presentation.common

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bondcalculator.presentation.fragments.PayoutsFragment
import com.example.bondcalculator.presentation.fragments.PortfolioFragment
import com.example.bondcalculator.presentation.fragments.PurchaseHistoryFragment

class ChardsFrgAdapterVp2(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    private val listFragments: List<Fragment> =
        listOf(PortfolioFragment(), PayoutsFragment(), PurchaseHistoryFragment())

    override fun getItemCount(): Int = listFragments.size

    override fun createFragment(position: Int): Fragment {
        return listFragments[position]
    }
}