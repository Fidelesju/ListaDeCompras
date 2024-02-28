package com.example.listacompras.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompras.R
import com.example.listacompras.data.entity.HistoricSales
import com.example.listacompras.data.entity.Products

class HistoricDetailAdapter(
    private var listFromDatabase: (historicSales : HistoricSales) -> Unit
) : ListAdapter<HistoricSales, HistoricDetailViewHolder>(HistoricListAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricDetailViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_historic_detail, parent, false)
        return HistoricDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoricDetailViewHolder, position: Int) {
        val historicSales = getItem(position)
        holder.bind(historicSales, listFromDatabase)
    }

    companion object : DiffUtil.ItemCallback<HistoricSales>() {
        override fun areItemsTheSame(oldItem: HistoricSales, newItem: HistoricSales): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HistoricSales, newItem: HistoricSales): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.value == newItem.value
        }
    }
}

class HistoricDetailViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val tvProduct = view.findViewById<TextView>(R.id.tv_product_historic)
    private val tvDate = view.findViewById<TextView>(R.id.tv_date_product)
    private val tvValueProduct = view.findViewById<TextView>(R.id.tv_value_product)
    private val tvCountProduct = view.findViewById<TextView>(R.id.tv_count_product)

    fun bind(
        historicSales: HistoricSales,
        listFromDatabase: (historicSales : HistoricSales) -> Unit
    ) {

        tvProduct.text = historicSales.title
        tvDate.text = historicSales.dateSales
        tvValueProduct.text = historicSales.value
        tvCountProduct.text = historicSales.count

        view.setOnClickListener {
            listFromDatabase.invoke(historicSales)
        }
    }
}
