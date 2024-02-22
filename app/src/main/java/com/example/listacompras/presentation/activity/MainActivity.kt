package com.example.listacompras.presentation.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.fragment.app.commit
import com.example.listacompras.R
import com.example.listacompras.data.Products
import com.example.listacompras.presentation.fragment.CarShoppingFragment
import com.example.listacompras.presentation.fragment.ProductsListFragment
import com.example.listacompras.presentation.fragment.SalesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val productsListFragment = ProductsListFragment.newInstance()
        val salesListFragment = SalesFragment.newInstance()
        val carShoppingFragment = CarShoppingFragment.newInstance()
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floating_action_button)

        floatingActionButton.setOnClickListener {
            openProductListDetail()
        }

        supportFragmentManager.commit {
            replace(R.id.fragment_container_view_product, productsListFragment)
            setReorderingAllowed(true)
        }

        bottomNavView.setOnItemSelectedListener {
            val fragment = when (it.itemId) {
                R.id.products_list -> productsListFragment
                R.id.sales_list -> salesListFragment
                R.id.car_list -> carShoppingFragment
                else -> productsListFragment
            }
            supportFragmentManager.commit {
                replace(R.id.fragment_container_view_product, fragment)
                setReorderingAllowed(true)
            }
            true
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