package com.example.listacompras.presentation.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompras.R
import com.example.listacompras.data.Products
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

    // Cor padrão ou cor original do FloatingActionButton
    val unpressed = ctnProducts.backgroundTintList

    fun bind(
        products: Products,
        openProductListDetail: (product: Products) -> Unit
    ) {
        val context = itemView.context

        fab.setOnClickListener {
            val pressed = ContextCompat.getColor(context, R.color.green)
            val unpressed = ContextCompat.getColor(context, R.color.white)


            if (ctnProducts.backgroundTintList == ColorStateList.valueOf(pressed)) {
                ctnProducts.backgroundTintList = ColorStateList.valueOf(unpressed)
            } else {
                ctnProducts.backgroundTintList = ColorStateList.valueOf(pressed)
            }
            // Adicionando a chamada para mostrar o diálogo de quantidade
            mostrarPopupQuantidade(products, context)

        }

        tvProducts.text = products.title
        tvCategory.text = products.category

        view.setOnClickListener {
            openProductListDetail.invoke(products)
        }
    }

    // Função para mostrar o popup de quantidade
    private fun mostrarPopupQuantidade(products: Products, context: Context) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_count, null)
        val editTextQuantidade = dialogView.findViewById<EditText>(R.id.editTextQuantidade)

        builder.setView(dialogView)
            .setTitle("Digite a quantidade para ${products.title}")
            .setPositiveButton("OK") { dialog, _ ->
                val quantidade = editTextQuantidade.text.toString().toIntOrNull() ?: 0
                // Agora você pode usar a quantidade conforme necessário
                Toast.makeText(context, "Quantidade: $quantidade", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

}
