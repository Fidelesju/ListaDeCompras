package com.example.listacompras.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompras.R
import com.example.listacompras.data.Products

class ProductListAdapter(
    private val openProductListDetail: (product : Products) -> Unit
) : ListAdapter<Products, ProductListViewHolder>(ProductListAdapter) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_product_list, parent, false)
        return ProductListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val products = getItem(position)
        holder.bind(products)
    }

    companion object : DiffUtil.ItemCallback<Products>() {
        override fun areItemsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Products, newItem: Products): Boolean {
            return oldItem.title == newItem.title &&
                    oldItem.category == newItem.category
        }
    }
}

class ProductListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    private val tvProducts = view.findViewById<TextView>(R.id.tv_product_title)
    private val tvCategory = view.findViewById<TextView>(R.id.tv_product_category)
    fun bind(
        products: Products
    ) {
        tvProducts.text = products.title
        tvCategory.text = products.category
    }
}