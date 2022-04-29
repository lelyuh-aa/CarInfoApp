package com.lelyuh.testcarapp.presentation.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lelyuh.testcarapp.R
import com.lelyuh.testcarapp.presentation.adapter.BackgroundHelper.coloredListBackground

internal class CarsSimpleDataViewHolder(
    private val listener: CarsSimlpeDataListAdapter.OnItemClickListener,
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(data: String, isEvenItem: Boolean) {
        itemView.background = coloredListBackground(itemView.context, isEvenItem)
        itemView.findViewById<TextView>(R.id.info_text_view).text = data
        itemView.setOnClickListener {
            listener.onItemClick(data)
        }
    }
}