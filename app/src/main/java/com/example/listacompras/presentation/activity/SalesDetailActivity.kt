package com.example.listacompras.presentation.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.listacompras.R
import com.example.listacompras.data.entity.Sales
import com.example.listacompras.presentation.action.ActionType
import com.example.listacompras.presentation.action.SalesAction
import com.example.listacompras.presentation.viewModel.SalesDetailViewModel
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date

class SalesDetailActivity : AppCompatActivity() {

    private var sales: Sales? = null
    private val decimalFormat = DecimalFormat("#,###")
    lateinit var edtCount: EditText
    lateinit var edtValue: EditText
    private val viewModel: SalesDetailViewModel by viewModels {
        SalesDetailViewModel.getVMFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        sales = intent.getSerializableExtra(SALES_DETAIL_EXTRA) as? Sales

        edtCount = findViewById(R.id.edt_count)
        edtValue = findViewById(R.id.edt_value)

        val btnCart: Button = findViewById(R.id.btn_cart)
        val edtObservation: EditText = findViewById(R.id.edt_observation)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        val titleProduct = sales?.title

        // Define o título da Toolbar com o valor do produto
        toolbar.title = titleProduct

        // Utilizando elvis operator para definir count como 0 caso seja null
        var count = sales?.count?.toFloat() ?: 0.0f

        // Utilizando setText para definir o texto diretamente
        edtCount.setText(count.toString())
        edtValue.setText(sales?.value)

        btnCart.setOnClickListener {
            val value = edtValue.text.toString()
            val observation = edtObservation.text.toString()
            val title = sales?.title.orEmpty()
            val id = sales?.id ?: 0
            val today = convertDate()
            val countEdit = edtCount.text

            if (value.isNotEmpty()) {
                if (count.toString() == countEdit.toString()) {
                    updateSales(
                        id,
                        title,
                        count.toString(),
                        value,
                        observation,
                        today,
                        ActionType.UPDATE
                    )
                } else {
                    updateSales(
                        id,
                        title,
                        countEdit.toString(),
                        value,
                        observation,
                        today,
                        ActionType.UPDATE
                    )
                }
            } else {
                showMessage(it, getString(R.string.fill_fields))
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    companion object {
        private const val SALES_DETAIL_EXTRA = "sales.extra.detail"

        fun start(context: Context, sales: Sales?): Intent {
            return Intent(context, SalesDetailActivity::class.java).apply {
                putExtra(SALES_DETAIL_EXTRA, sales)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_sales_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_sales -> {
                if (sales != null) {
                    performAction(sales!!, ActionType.DELETE)
                } else {
                    println("Deu erro onOptionsItemSelected")
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun convertDate(): String {
        val format = SimpleDateFormat("dd/MM/yyyy")
        val date = Date()
        return format.format(date)
    }

    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }

    private fun updateSales(id: Int, title: String, count: String, value: String, obs: String, date: String, actionType: ActionType) {
        val sales = Sales(id, title, count, obs, value, date, 0)
        performAction(sales, actionType)
    }

    private fun performAction(sales: Sales?, actionType: ActionType) {
        val salesAction = SalesAction(sales!!, actionType.name)
        viewModel.execute(salesAction)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
