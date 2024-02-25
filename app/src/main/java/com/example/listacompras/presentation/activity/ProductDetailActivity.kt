package com.example.listacompras.presentation.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.widget.Adapter
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
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //region variables
        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floating_action_button)
        val btnApply: Button = findViewById(R.id.btn_apply)
        val btnDelete: Button = findViewById(R.id.btn_delete)
        val edtProduct: EditText = findViewById(R.id.edt_product)
        val spnCategory: Spinner = findViewById(R.id.spn_category)
        val category = listOf("Frutas e Verduras", "Limpeza", "Mantimento", "Frios", "Carne")

        //endregion

        products = intent.getSerializableExtra(PRODUCT_DETAIL_EXTRA) as? Products

        //region adapter spinner
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, category)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnCategory.adapter = adaptador
        //endregion

        //region events
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

        btnDelete.setOnClickListener {
            if (products != null) {
                performAction(products!!, ActionType.DELETE)
            } else {
                showMessage(it, "Não há produtos para serem excluidos")
                Log.d("ERRO", " btnDelete.setOnClickListener ")
            }
        }
        setTextSetOnClickListener(products, edtProduct, spnCategory, adaptador)

        //endregion
    }

    private fun showPopup(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun setTextSetOnClickListener(
        products: Products?,
        editText: EditText,
        spinner: Spinner,
        adapter: ArrayAdapter<String>
    ) {
        if (products != null) {
            editText.setText(products!!.title)
            val position = adapter.getPosition(products!!.category)
            spinner.setSelection(position)
        }
    }

    private fun addOrUpdateTask(
        id: Int, title: String, category: String, actionType: ActionType
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
            val intent = Intent(context, ProductDetailActivity::class.java).apply {
                putExtra(PRODUCT_DETAIL_EXTRA, products)
            }
            return intent
        }
    }

    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }


    private fun performAction(products: Products, actionType: ActionType) {

        val productAction = ProductAction(products, actionType.name)
        viewModel.execute(productAction)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}