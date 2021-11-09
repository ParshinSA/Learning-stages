package com.example.roomwordssample.ui.word.word_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomwordssample.R
import com.example.roomwordssample.data.db.word_model.Word

class AdapterWordListRV() : ListAdapter<Word, AdapterWordListRV.Holder>(DiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_word, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffUtilItemCallback : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.wordText == newItem.wordText
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }
    }

    class Holder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        private val wordTv: TextView = view.findViewById(R.id.word_tv)

        fun bind(word: Word) {
            wordTv.text = word.wordText
        }
    }

}