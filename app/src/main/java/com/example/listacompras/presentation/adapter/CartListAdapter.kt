package com.example.listacompras.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompras.R
import com.example.listacompras.data.entity.Sales

class CartListAdapter(
    private val openSalesListDetail: (sales: Sales) -> Unit,
) : ListAdapter<Sales, CartListViewHolder>(SalesAdapter) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_cart_list, parent, false)
        return CartListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        val sales = getItem(position)
        holder.bind(sales, openSalesListDetail)
    }

    companion object : DiffUtil.ItemCallback<Sales>() {
        override fun areItemsTheSame(oldItem: Sales, newItem: Sales): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Sales, newItem: Sales): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.count == newItem.count &&
                    oldItem.value == newItem.value
        }
    }
}

class CartListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val tvTitleSalesCart = view.findViewById<TextView>(R.id.tv_product_title_car)
    private val tvTotalSales = view.findViewById<TextView>(R.id.tv_total_sales)


    fun bind(
        sales: Sales,
        openSalesListDetail: (sales: Sales) -> Unit
    ) {
        val context = itemView.context

        val totalProduct = calculatingPrice(sales.count, sales.value)

        tvTitleSalesCart.text = sales.title
        tvTotalSales.text = totalProduct.toString()

        view.setOnClickListener {
            openSalesListDetail.invoke(sales)
        }
    }

    fun calculatingPrice(count: String, value: String): Float {
        val countInt = count.toInt()
        val valueInt = value.toFloat()
        val total = countInt * valueInt

        return total
    }
}
