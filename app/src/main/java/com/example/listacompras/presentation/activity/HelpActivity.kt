package com.example.listacompras.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.listacompras.R

class HelpActivity : AppCompatActivity() {

    lateinit var ctnHelpSales : LinearLayout
    lateinit var ctnHelpProducts : LinearLayout
    lateinit var ctnHelpCart : LinearLayout
    lateinit var ctnHelpHistoric: LinearLayout
    lateinit var cvHelpProducts : CardView
    lateinit var cvHelpSales : CardView
    lateinit var cvHelpCart : CardView
    lateinit var cvHelpHistoric: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        ctnHelpSales = findViewById(R.id.ctn_help_sales)
        ctnHelpProducts = findViewById(R.id.ctn_help_products)
        ctnHelpCart = findViewById(R.id.ctn_help_cart)
        ctnHelpHistoric = findViewById(R.id.ctn_help_historic)
        cvHelpProducts = findViewById(R.id.cv_help_products)
        cvHelpSales = findViewById(R.id.cv_help_sales)
        cvHelpCart = findViewById(R.id.cv_help_cart)
        cvHelpHistoric = findViewById(R.id.cv_help_historic)

        // Configurar OnClickListener para cvHelpProducts
        cvHelpProducts.setOnClickListener {
            toggleVisibility(ctnHelpProducts)
        }

        // Configurar OnClickListener para cvHelpSales
        cvHelpSales.setOnClickListener {
            toggleVisibility(ctnHelpSales)
        }

        // Configurar OnClickListener para cvHelpCart
        cvHelpCart.setOnClickListener {
            toggleVisibility(ctnHelpCart)
        }

        // Configurar OnClickListener para cvHelpHistoric
        cvHelpHistoric.setOnClickListener {
            toggleVisibility(ctnHelpHistoric)
        }
    }
    private fun toggleVisibility(linearLayout: LinearLayout) {
        // Trocar a visibilidade do LinearLayout
        linearLayout.visibility = if (linearLayout.visibility == View.VISIBLE) View.GONE else View.VISIBLE
    }

}