package com.example.weatherapplication.app.ui.common

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.app.common.fromDpToPixels

/**
 * Устанавливаем расстояние между View-элементами в RecyclerView
 * @author Parshin Sergey
 * @param offsetDp значение расстояния в dp
 * @property fromDpToPixels конвертация dp в pixel
 * @property getItemOffsets установка полученных значений
 *  */

class ItemDecoration(
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
