package com.example.listacompras.presentation.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Date

class SalesDetailActivity : AppCompatActivity() {

    private var sales: Sales? = null
    lateinit var edtCount : EditText
    private val viewModel: SalesDetailViewModel by viewModels {
        SalesDetailViewModel.getVMFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)
        sales = intent.getSerializableExtra(SALES_DETAIL_EXTRA) as? Sales

        edtCount = findViewById(R.id.edt_count)
        val btnLess: FloatingActionButton = findViewById(R.id.btn_less)
        val btnPlus: FloatingActionButton = findViewById(R.id.btn_plus)
        val btnCart: Button = findViewById(R.id.btn_cart)
        val edtObservation: EditText = findViewById(R.id.edt_observation)
        val edtValue: EditText = findViewById(R.id.edt_value)

        // Utilizando elvis operator para definir count como 0 caso seja null
        var count = sales?.count?.toFloat() ?: 0.0f

        // Utilizando setText para definir o texto diretamente
        edtCount.setText(count.toString())

        btnPlus.setOnClickListener {
            // Incrementa count
            count += 1
            edtCount.setText(count.toString())
        }

        btnLess.setOnClickListener {
            // Decrementa count, evitando valores negativos
            if (count > 0) {
                count -= 1
                edtCount.setText(count.toString())
            }
        }

        btnCart.setOnClickListener {
            val value = edtValue.text.toString()
            val observation = edtObservation.text.toString()
            val title = sales?.title.orEmpty()
            val id = sales?.id ?: 0
            val today = convertDate()
            val countEdit = edtCount.text

            if (value.isNotEmpty()) {
                if (count.toString() == countEdit.toString())
                {
                    updateSales(id, title, count.toString(), value, observation, today, ActionType.UPDATE)
                }
                else
                {
                    updateSales(id, title, countEdit.toString(), value, observation, today, ActionType.UPDATE)
                }
            } else {
                showMessage(it, getString(R.string.fill_fields))
            }
        }
    }

    companion object {
        private const val SALES_DETAIL_EXTRA = "sales.extra.detail"

        fun start(context: Context, sales: Sales?): Intent {
            return Intent(context, SalesDetailActivity::class.java).apply {
                putExtra(SALES_DETAIL_EXTRA, sales)
            }
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
