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
import com.example.listacompras.presentation.adapter.HistoricDetailAdapter
import com.example.listacompras.presentation.viewModel.HistoricSalesDetailViewModel

class HistoricSalesDetailActivity : AppCompatActivity() {

    private lateinit var historicSales: HistoricSales
    private val viewModel: HistoricSalesDetailViewModel by viewModels {
        HistoricSalesDetailViewModel.getVMFactory(application)
    }

    private val adapter: HistoricDetailAdapter by lazy {
        HistoricDetailAdapter(::listFromDatabase)
    }

    companion object {
        private const val HISTORIC_DETAIL_EXTRA = "historic.extra.detail"

        fun start(context: Context, products: HistoricSales): Intent {
            return Intent(context, HistoricSalesDetailActivity::class.java).apply {
                putExtra(HISTORIC_DETAIL_EXTRA, products)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historic_sales_detail)
        val rvHistoricDetail: RecyclerView = findViewById(R.id.rv_historic_detail)

        historicSales =
            intent.getSerializableExtra(HistoricSalesDetailActivity.HISTORIC_DETAIL_EXTRA) as HistoricSales

        listFromDatabase(historicSales)
        setupRecyclerView(rvHistoricDetail)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
    }

    private fun listFromDatabase(historicSales: HistoricSales) {
        // Chama o método para obter a lista no ViewModel
        viewModel.getAllHistoricByDateAndSupermarket(
            historicSales.dateSales,
            historicSales.superMarketing
        )

        // Observador para a lista de históricos
        val listObserver = Observer<List<HistoricSales>> { historicList ->
            historicList?.takeIf { it.isNotEmpty() }?.let {
                adapter.submitList(it)
            } ?: run {
                println("A lista de históricos está vazia ou nula")
            }
        }

        // Adiciona o observador após chamar o método no ViewModel
        viewModel.historicSalesDetailLiveData.observe(this, listObserver)
    }

}
