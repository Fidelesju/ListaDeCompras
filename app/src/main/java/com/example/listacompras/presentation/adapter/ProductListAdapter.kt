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
import com.example.listacompras.data.entity.Products
import com.example.listacompras.data.entity.Sales
import com.example.listacompras.presentation.action.ActionType
import com.example.listacompras.presentation.action.SalesAction
import com.example.listacompras.presentation.viewModel.SalesDetailViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProductListAdapter(
    private val openProductListDetail: (product: Products) -> Unit
) : ListAdapter<Products, ProductListViewHolder>(ProductListAdapter) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_product_list, parent, false)
        return ProductListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        val products = getItem(position)
        holder.bind(products, openProductListDetail)

    }

    // Retorna o tipo de visualização com base na posição
    override fun getItemViewType(position: Int): Int {
        val products = getItem(position)
        return R.layout.item_product_list
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
    private val fab: FloatingActionButton = view.findViewById(R.id.floating_action_button_product)
    private val ctnProducts: CardView = view.findViewById(R.id.ctn_products)

    //    private val tvCategoryTitle = view.findViewById<TextView>(R.id.tv_category_title)
    val context = itemView.context
    val pressed = ContextCompat.getColor(context, R.color.green_light)
    val unpressed = ContextCompat.getColor(context, R.color.white)

    fun bind(
        products: Products,
        openProductListDetail: (product: Products) -> Unit
    ) {

        fab.setOnClickListener {
            if (ctnProducts.backgroundTintList == ColorStateList.valueOf(pressed)) {
                ctnProducts.backgroundTintList = ColorStateList.valueOf(unpressed)
            } else {
                ctnProducts.backgroundTintList = ColorStateList.valueOf(pressed)
                showPopupCount(products, context)
            }
        }

        tvProducts.text = products.title
        tvCategory.text = products.category

        view.setOnClickListener {
            openProductListDetail.invoke(products)
        }
    }


    // Função para mostrar o popup de quantidade
    private fun showPopupCount(products: Products, context: Context) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_count, null)
        val editTextQuantidade = dialogView.findViewById<EditText>(R.id.editTextQuantidade)

        builder.setView(dialogView)
            .setTitle("Digite a quantidade para ${products.title}.")
            .setPositiveButton("OK") { dialog, _ ->
                val count = editTextQuantidade.text.toString()
                val value = 0
                if (count.isNotEmpty()) {
                    // Criar um novo Sales e inserir no banco de dados
                    val newSales = Sales(0, products.title, count, value.toString(), "", "",1)
                    val salesViewModel = ViewModelProvider(
                        context as FragmentActivity,
                        SalesDetailViewModel.getVMFactory(context.application)
                    )
                        .get(SalesDetailViewModel::class.java)
                    salesViewModel.execute(SalesAction(newSales, ActionType.CREATE.name))
                } else {
                    Toast.makeText(
                        context,
                        "Quantidade deve ser maior que zero",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
