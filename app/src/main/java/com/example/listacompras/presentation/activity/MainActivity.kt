package com.example.listacompras.presentation.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.runtime.Composable
import com.example.listacompras.R
import com.example.listacompras.data.Products
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floating_action_button)

        floatingActionButton.setOnClickListener {
            openProductListDetail()
        }

    }


    private fun openProductListDetail() {
        val intent = ProductDetailActivity.start(this, null)
        startActivity(intent)
    }

    companion object {
        fun start(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            return intent
        }
    }

}