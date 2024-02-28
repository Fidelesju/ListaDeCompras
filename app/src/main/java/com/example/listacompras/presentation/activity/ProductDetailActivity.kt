package com.example.listacompras.presentation.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.listacompras.R
import com.example.listacompras.data.entity.Products
import com.example.listacompras.presentation.action.ActionType
import com.example.listacompras.presentation.action.ProductAction
import com.example.listacompras.presentation.viewModel.ProductDetailViewModel
import com.google.android.material.snackbar.Snackbar

class ProductDetailActivity : AppCompatActivity() {

    private var products: Products? = null

    //region teste
    private val PREFS_NAME = "YourPrefsFile"
    private val KEY_FIRST_RUN = "firstRun"
    //endregion

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
        val btnApply: Button = findViewById(R.id.btn_apply)
        val btnDelete: Button = findViewById(R.id.btn_delete)
        val edtProduct: EditText = findViewById(R.id.edt_product)
        val spnCategory: Spinner = findViewById(R.id.spn_category)

        val category = listOf("Bebidas","Carnes","Cereais e Lanches","Condimentos e Temperos","Cuidados Pessoais","Frutas e Verduras", "Laticínios","Limpeza", "Padaria","Mantimento")

        //endregion

        products = intent.getSerializableExtra(PRODUCT_DETAIL_EXTRA) as? Products

        //region adapter spinner
        val adaptador = ArrayAdapter(this, android.R.layout.simple_spinner_item, category)
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnCategory.adapter = adaptador
        //endregion

        //region events

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
                showMessage(it, getString(R.string.fill_fields))
            }

            if (isFirstRun()) {
                val exampleProducts = generateExampleProductList()

                for (product in exampleProducts) {
                    addOrUpdateTask(product.id, product.title, product.category, ActionType.CREATE)
                }

                setFirstRunFlag()
            }

        }

        btnDelete.setOnClickListener {
            if (products != null) {
                performAction(products!!, ActionType.DELETE)
            } else {
                showMessage(it, getString(R.string.no_products_to_delete))
            }
        }
        setTextSetOnClickListener(products, edtProduct, spnCategory, adaptador)

        //endregion

    }

    private fun isFirstRun(): Boolean {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_FIRST_RUN, true)
    }

    private fun setFirstRunFlag() {
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(KEY_FIRST_RUN, false)
        editor.apply()
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

    private fun generateExampleProductList(): List<Products> {
        val exampleProducts = listOf(
            Products(title = "Coca-Cola", category = "Bebidas"),
            Products(title = "Frango", category = "Carnes"),
            Products(title = "Cereal Matinal", category = "Cereais e Lanches"),
            Products(title = "Sal", category = "Condimentos e Temperos"),
            Products(title = "Shampoo", category = "Cuidados Pessoais"),
            Products(title = "Maçã", category = "Frutas e Verduras"),
            Products(title = "Leite", category = "Laticínios"),
            Products(title = "Detergente", category = "Limpeza"),
            Products(title = "Pão", category = "Padaria"),
            Products(title = "Arroz", category = "Mantimento"),
            Products(title = "Refrigerante", category = "Bebidas"),
            Products(title = "Carne de Porco", category = "Carnes"),
            Products(title = "Granola", category = "Cereais e Lanches"),
            Products(title = "Azeite", category = "Condimentos e Temperos"),
            Products(title = "Sabonete", category = "Cuidados Pessoais"),
            Products(title = "Banana", category = "Frutas e Verduras"),
            Products(title = "Queijo", category = "Laticínios"),
            Products(title = "Limpador Multiuso", category = "Limpeza"),
            Products(title = "Baguete", category = "Padaria"),
            Products(title = "Feijão", category = "Mantimento"),
            Products(title = "Água Mineral", category = "Bebidas"),
            Products(title = "Carne de Boi", category = "Carnes"),
            Products(title = "Barra de Cereal", category = "Cereais e Lanches"),
            Products(title = "Vinagre", category = "Condimentos e Temperos"),
            Products(title = "Creme Dental", category = "Cuidados Pessoais"),
            Products(title = "Uva", category = "Frutas e Verduras"),
            Products(title = "Iogurte", category = "Laticínios"),
            Products(title = "Desinfetante", category = "Limpeza"),
            Products(title = "Rosquinha", category = "Padaria"),
            Products(title = "Macarrão", category = "Mantimento"),
            // Adicione mais produtos conforme necessário
        )


        return exampleProducts
    }

    private fun addOrUpdateTask(
        id: Int, title: String, category: String, actionType: ActionType
    ) {
        val products = Products(id, title, category)
        performAction(products, actionType)
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