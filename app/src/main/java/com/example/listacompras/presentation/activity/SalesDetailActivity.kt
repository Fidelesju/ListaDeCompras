package com.example.listacompras.presentation.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.listacompras.R
import com.example.listacompras.data.entity.Sales
import com.example.listacompras.presentation.action.ActionType
import com.example.listacompras.presentation.action.SalesAction
import com.example.listacompras.presentation.viewModel.SalesDetailViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date

class SalesDetailActivity : AppCompatActivity() {

    private var sales: Sales? = null

    private val viewModel: SalesDetailViewModel by viewModels {
        SalesDetailViewModel.getVMFactory(
            application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)
        sales = intent.getSerializableExtra(SALES_DETAIL_EXTRA) as? Sales

        val tvCount: TextView = findViewById(R.id.tv_count)
        val btnLess: FloatingActionButton = findViewById(R.id.btn_less)
        val btnPlus: FloatingActionButton = findViewById(R.id.btn_plus)
        val btnCart: Button = findViewById(R.id.btn_cart)
        val edtObservation: EditText = findViewById(R.id.edt_observation)
        val edtValue: EditText = findViewById(R.id.edt_value)
        var count = (sales?.count ?: 0).toString().toInt()

        tvCount.text = count.toString()

        btnPlus.setOnClickListener {
            // Incrementa count
            count += 1
            tvCount.text = count.toString()
        }

        btnLess.setOnClickListener {
            // Decrementa count, evitando valores negativos
            if (count > 0) {
                count -= 1
                tvCount.text = count.toString()
            }
        }

        btnCart.setOnClickListener {
            val value = edtValue.text.toString()
            val observation = edtObservation.text.toString()
            val count = (tvCount.text).toString()
            val title = (sales?.title).orEmpty()
            val id = sales?.id ?: 0
            val today = convertDate()

            if (value.isNotEmpty()) {
                updateSales(
                    id,
                    title,
                    count,
                    value,
                    observation,
                    today,
                    ActionType.UPDATE
                )
            } else {
                showMessage(it, "Fields are required")
                println("Deu erro setOnClickListener")
            }
        }
    }

    companion object {
        private const val SALES_DETAIL_EXTRA = "sales.extra.detail"

        fun start(context: Context, sales: Sales?): Intent {
            val intent = Intent(context, SalesDetailActivity::class.java).apply {
                putExtra(SALES_DETAIL_EXTRA, sales)
            }
            return intent
        }
    }

    fun convertDate(): String {
        val format = SimpleDateFormat("dd/MM/yyyy")
        val date = Date()
        return format.format(date)
    }

    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }

    private fun updateSales(
        id: Int,
        title: String,
        count: String,
        value: String,
        obs: String,
        date: String,
        actionType: ActionType
    ) {
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