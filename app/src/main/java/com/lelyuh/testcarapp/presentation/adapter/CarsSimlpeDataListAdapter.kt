package com.lelyuh.testcarapp.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lelyuh.testcarapp.R

/**
 * Adapter for lists with simple UI (only one string field)
 *
 * @author Leliukh Aleksandr
 */
internal class CarsSimlpeDataListAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CarsSimpleDataViewHolder>() {

    private var modelsList: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsSimpleDataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.common_list_item, parent, false)
        return CarsSimpleDataViewHolder(listener, view)
    }

    override fun onBindViewHolder(holder: CarsSimpleDataViewHolder, position: Int) {
        holder.bind(modelsList[position], position.mod(MODULUS_2) == 0)
    }

    override fun getItemCount() = modelsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateListData(newList: List<String>) {
        modelsList = ArrayList(newList)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {

        fun onItemClick(data: String)
    }

    private companion object {
        private const val MODULUS_2 = 2
    }
}