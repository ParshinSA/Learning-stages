package com.example.bondcalculator.presentation.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bondcalculator.R
import com.example.bondcalculator.databinding.ItemPayoutsRvBinding
import com.example.bondcalculator.presentation.models.PayoutsItemRv

class PayoutsItemListAdapterRv :
    ListAdapter<PayoutsItemRv, PayoutsItemListAdapterRv.Holder>(DiffUtilItemCallback()) {

    class DiffUtilItemCallback : DiffUtil.ItemCallback<PayoutsItemRv>() {
        override fun areItemsTheSame(oldItem: PayoutsItemRv, newItem: PayoutsItemRv): Boolean {
            return oldItem.year == newItem.year
        }

        override fun areContentsTheSame(oldItem: PayoutsItemRv, newItem: PayoutsItemRv): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_payouts_rv, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        return holder.bind(getItem(position))
    }

    class Holder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        private val binding = ItemPayoutsRvBinding.bind(view)

        fun bind(item: PayoutsItemRv) {

            binding.textViewTxtYear.text = item.year
            binding.textViewTxtSumYield.text = item.sumYield.toString()

            val set = ConstraintSet()
            with(set) {

                constrainPercentWidth(
                    R.id.imageViewPercentRedemptionYield,
                    item.percentRedemptionYield
                )
                constrainPercentHeight(R.id.imageViewPercentRedemptionYield, 0.5f)
                applyTo(binding.containerPercentRedemptionYield)

                constrainPercentWidth(R.id.imageViewPercentCouponYield, item.percentCouponYield)
                constrainPercentHeight(R.id.imageViewPercentCouponYield, 0.5f)
                applyTo(binding.containerPercentCouponYield)
            }
        }
    }
}
