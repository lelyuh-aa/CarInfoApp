package com.lelyuh.testcarapp.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lelyuh.testcarapp.R
import com.lelyuh.testcarapp.models.presentation.CarModel

/**
 * Adapter for manufacturer list with support paged loading
 *
 * @author Leliukh Aleksandr
 */
internal class CarsListAdapter(
    private val listener: OnItemHanlderListener
) : RecyclerView.Adapter<CarsListViewHolder>() {

    private var modelsList: List<CarModel> = emptyList()
    private var currentPositionForLoading = END_PAGE_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarsListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.common_list_item, parent, false)
        return CarsListViewHolder(listener, view)
    }

    override fun onBindViewHolder(holder: CarsListViewHolder, position: Int) {
        holder.bind(modelsList[position], position.mod(MODULUS_2) == 0)
        if (position == currentPositionForLoading) {
            currentPositionForLoading += END_PAGE_POSITION
            listener.onNextPageLoad()
        }
    }

    override fun getItemCount() = modelsList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateListData(newList: List<CarModel>) {
        modelsList = ArrayList(newList)
        notifyDataSetChanged()
    }

    interface OnItemHanlderListener {

        fun onNextPageLoad() {}

        fun onItemClick(model: CarModel)
    }

    private companion object {
        private const val MODULUS_2 = 2
        private const val END_PAGE_POSITION = 14
    }
}