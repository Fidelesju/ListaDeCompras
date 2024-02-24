package com.example.listacompras.presentation.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.viewModels
import com.example.listacompras.R
import com.example.listacompras.data.Products
import com.example.listacompras.presentation.ActionType
import com.example.listacompras.presentation.ProductAction
import com.example.listacompras.presentation.viewModel.ProductDetailViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ProductDetailActivity : AppCompatActivity() {

    private var products: Products? = null

    private val viewModel: ProductDetailViewModel by viewModels {
        ProductDetailViewModel.getVMFactory(
            application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floating_action_button)
        val btnApply: Button = findViewById(R.id.btn_apply)
        val edtProduct: EditText = findViewById(R.id.edt_product)
        val spnCategory: Spinner = findViewById(R.id.spn_category)
        val category = listOf("Frutas e Verduras", "Limpeza", "Mantimento", "Frios", "Carne")

        // Cria um adaptador e define os dados para o Spinner
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, category)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnCategory.adapter = adaptador

        products = intent.getSerializableExtra(PRODUCT_DETAIL_EXTRA) as? Products

        if (products != null) {
            edtProduct.setText(products!!.title)
//            spnCategory.setS(task!!.description)
        }

        floatingActionButton.setOnClickListener {
            openMainActivity()
        }

        btnApply.setOnClickListener {
            val title = edtProduct.text.toString()
            val category = spnCategory.selectedItem.toString()

            if (title.isNotEmpty() && category.isNotEmpty()) {
                if (products == null) {
                    addOrUpdateTask(0, title, category, ActionType.CREATE)
                } else {
                    addOrUpdateTask(products!!.id, title, category, ActionType.UPDATE)
                }
            } else {
                showMessage(it, "Fields are required")
                println("Deu erro setOnClickListener")
            }
        }
    }

    private fun addOrUpdateTask(
        id: Int,
        title: String,
        category: String,
        actionType: ActionType
    ) {
        val products = Products(id, title, category)
        performAction(products, actionType)
    }

    private fun openMainActivity() {
        val intent = MainActivity.start(this)
        startActivity(intent)
    }


    companion object {
        private const val PRODUCT_DETAIL_EXTRA = "product.extra.detail"

        fun start(context: Context, products: Products?): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
                .apply {
                    putExtra(PRODUCT_DETAIL_EXTRA, products)
                }
            return intent
        }
    }

    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

    private fun performAction(products: Products, actionType: ActionType) {

        val productAction = ProductAction(products, actionType.name)
        viewModel.execute(productAction)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}