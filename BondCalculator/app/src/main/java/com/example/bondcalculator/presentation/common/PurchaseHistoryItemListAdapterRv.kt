package com.example.bondcalculator.presentation.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bondcalculator.R
import com.example.bondcalculator.databinding.ItemPurchaseHistoryRvBinding
import com.example.bondcalculator.presentation.models.PurchaseHistoryItemRv

class PurchaseHistoryItemListAdapterRv :
    ListAdapter<PurchaseHistoryItemRv, PurchaseHistoryItemListAdapterRv.Holder>(DiffUtilItemCallback()) {

    class DiffUtilItemCallback : DiffUtil.ItemCallback<PurchaseHistoryItemRv>() {
        override fun areItemsTheSame(
            oldItem: PurchaseHistoryItemRv,
            newItem: PurchaseHistoryItemRv
        ): Boolean {
            return oldItem.year == newItem.year
        }

        override fun areContentsTheSame(
            oldItem: PurchaseHistoryItemRv,
            newItem: PurchaseHistoryItemRv
        ): Boolean {
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_purchase_history_rv, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        private val binding = ItemPurchaseHistoryRvBinding.bind(view)

        fun bind(item: PurchaseHistoryItemRv) {
            binding.linearLayoutContainerView.weightSum =
                item.percentSumPayments + item.percentPreviousYearPayment + item.percentTotal

            binding.textViewTxtYear.text = item.year

            setWeight(binding.viewPercentBuyBonds, item.percentTotal)
            setWeight(binding.viewPercentMoneyFromPastPeriods, item.percentPreviousYearPayment)
            setWeight(binding.viewPercentPaymentMoney, item.percentSumPayments)
        }

        private fun setWeight(view: View, weight: Float) {
            view.apply {
                layoutParams = LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.MATCH_PARENT, weight
                )
            }
        }
    }

}