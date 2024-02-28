package com.example.listacompras.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.listacompras.R
import com.example.listacompras.presentation.fragment.CartFragment
import com.example.listacompras.presentation.fragment.HistoricSalesFragment
import com.example.listacompras.presentation.fragment.ProductsListFragment
import com.example.listacompras.presentation.fragment.SalesListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private val productsListFragment = ProductsListFragment.newInstance()
    private val salesListFragment = SalesListFragment.newInstance()
    private val cartListFragment = CartFragment.newInstance()
    private val historicListFragment = HistoricSalesFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            val fragment = getFragmentForItemId(it.itemId)
            replaceFragment(fragment)
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_container_view_product, fragment)
            setReorderingAllowed(true)
        }
    }

    private fun getFragmentForItemId(itemId: Int): Fragment {
        return when (itemId) {
            R.id.products_list -> productsListFragment
            R.id.sales_list -> salesListFragment
            R.id.car_list -> cartListFragment
            R.id.historic_list -> historicListFragment
            else -> productsListFragment
        }
    }

    private fun openProductListDetail() {
        val intent = ProductDetailActivity.start(this, null)
        startActivity(intent)
    }
}
