package com.example.listacompras.presentation.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompras.R
import com.example.listacompras.data.entity.HistoricSales
import com.example.listacompras.data.entity.Products
import com.example.listacompras.data.entity.Sales
import com.example.listacompras.presentation.action.ActionType
import com.example.listacompras.presentation.action.SalesAction
import com.example.listacompras.presentation.viewModel.SalesDetailViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HistoricListAdapter(
    private val openHistoricListDetail: (historicSales: HistoricSales) -> Unit
) : ListAdapter<HistoricSales, HistoricListViewHolder>(HistoricListAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricListViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_historic_list, parent, false)
        return HistoricListViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoricListViewHolder, position: Int) {
        val historicSales = getItem(position)
        holder.bind(historicSales, openHistoricListDetail)
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

class HistoricListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val tvSuperMarketing = view.findViewById<TextView>(R.id.tv_sales_title)
    private val tvDate = view.findViewById<TextView>(R.id.tv_sales_date)
    private val tvTotal = view.findViewById<TextView>(R.id.tv_sales_total)

    fun bind(
        historicSales: HistoricSales,
        openHistoricListDetail: (historicSales: HistoricSales) -> Unit
    ) {

        tvSuperMarketing.text = historicSales.superMarketing
        tvDate.text = historicSales.dateSales
        tvTotal.text = historicSales.totalSales

        view.setOnClickListener {
            openHistoricListDetail.invoke(historicSales)
        }
    }


}
