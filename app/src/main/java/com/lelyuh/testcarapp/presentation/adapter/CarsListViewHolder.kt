package com.lelyuh.testcarapp.presentation.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.lelyuh.testcarapp.R
import com.lelyuh.testcarapp.models.presentation.CarModel
import com.lelyuh.testcarapp.presentation.adapter.BackgroundHelper.coloredListBackground

internal class CarsListViewHolder(
    private val listener: CarsListAdapter.OnItemHanlderListener,
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: CarModel, isEvenItem: Boolean) {
        itemView.background = coloredListBackground(itemView.context, isEvenItem)
        with(itemView.findViewById<ImageView>(R.id.manufacturer_icon_view)) {
            setImageResource(model.manufacturerIconResId)
            isVisible = true
        }
        itemView.findViewById<TextView>(R.id.info_text_view).text = model.manufacturerName
        itemView.setOnClickListener {
            listener.onItemClick(model)
        }
    }
}