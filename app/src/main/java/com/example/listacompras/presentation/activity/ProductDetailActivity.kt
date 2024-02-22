package com.example.listacompras.presentation.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.listacompras.R
import com.example.listacompras.data.Products
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floating_action_button)

        floatingActionButton.setOnClickListener {
            openMainActivity()
        }

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
}