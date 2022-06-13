package com.example.bondcalculator.presentation.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bondcalculator.R
import com.example.bondcalculator.databinding.ItemCompositionRvBinding
import com.example.bondcalculator.presentation.models.CompositionFrgItemRv

class CompositionFrgAdapterRv :
    ListAdapter<CompositionFrgItemRv, CompositionFrgAdapterRv.Holder>(DiffUtilItemCallback()) {

    class DiffUtilItemCallback : DiffUtil.ItemCallback<CompositionFrgItemRv>() {
        override fun areItemsTheSame(
            oldItem: CompositionFrgItemRv,
            newItem: CompositionFrgItemRv
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: CompositionFrgItemRv,
            newItem: CompositionFrgItemRv
        ): Boolean {
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_composition_rv, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class Holder(val view: View) : RecyclerView.ViewHolder(view) {

        val binding = ItemCompositionRvBinding.bind(view)

        fun bind(item: CompositionFrgItemRv) {
            binding.textViewNameBond.text = item.name

            binding.textViewCounterBonds.text = view.context.resources.getString(
                R.string.textViewTxtCounterBonds, item.amount
            )

            binding.textViewPriceBond.text = view.context.resources.getString(
                R.string.textViewTxtPriceBond,
                item.nominal.toInt(),
                String.format("%.2f", item.percentPrice)
            )

            binding.textViewCounterPercentPortfolio.text = view.context.getString(
                R.string.textViewTxtCounterPercentPortfolio, item.percentPortfolio
            )
        }

    }
}