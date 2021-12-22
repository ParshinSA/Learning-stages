package com.example.weatherapplication.ui.weather.shortforecastlist.recyclerview

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.utils.fromDpToPixels

class CharacterItemDecoration(
    private val context: Context,
    private val offsetDp: Int
) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val offSet = offsetDp.fromDpToPixels(context)
        with(outRect) {
            bottom = offSet
        }
    }
}
