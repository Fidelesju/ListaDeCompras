package com.example.listacompras.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompras.R
import com.example.listacompras.data.entity.HistoricSales
import com.example.listacompras.data.entity.Products
import com.example.listacompras.presentation.adapter.HistoricDetailAdapter
import com.example.listacompras.presentation.viewModel.HistoricSalesDetailViewModel

class HistoricSalesDetailActivity : AppCompatActivity() {

    private lateinit var historicSales: HistoricSales

    private val viewModel: HistoricSalesDetailViewModel by viewModels {
        HistoricSalesDetailViewModel.getVMFactory(
            application
        )
    }

    private val adapter: HistoricDetailAdapter by lazy {
        HistoricDetailAdapter(::listFromDatabase)
    }

    companion object {
        private const val HISTORIC_DETAIL_EXTRA = "historic.extra.detail"

        fun start(context: Context, products: HistoricSales): Intent {
            val intent = Intent(context, HistoricSalesDetailActivity::class.java).apply {
                putExtra(HISTORIC_DETAIL_EXTRA, products)
            }
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historic_sales_detail)
        val rvHistoricDetail: RecyclerView = findViewById(R.id.rv_historic_detail)

        historicSales = intent.getSerializableExtra(HistoricSalesDetailActivity.HISTORIC_DETAIL_EXTRA) as HistoricSales

        listFromDatabase(historicSales)
        setupRecyclerView(rvHistoricDetail)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
    }

    private fun listFromDatabase(historicSales: HistoricSales) {
        val listObserver = Observer<List<HistoricSales>> { historicList ->
            historicList?.let {
                if (it.isNotEmpty()) {
                    adapter.submitList(it)
                } else {
                    println("A lista de históricos está vazia")
                }
            } ?: println("A lista de históricos está nula")
        }


        // Verifique se o LiveData não é nulo antes de observá-lo
        if (viewModel.historicSalesDetailLiveData != null) {
            viewModel.getAllHistoricByDateAndSupermarket(historicSales.dateSales, historicSales.superMarketing)

            viewModel.historicSalesDetailLiveData?.let {
                it.observe(this, listObserver)
            } ?: println("LiveData de históricos está nulo")
        } else {
            println("LiveData de históricos está nulo")
        }
    }
}