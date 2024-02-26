package com.example.listacompras.presentation.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompras.R
import com.example.listacompras.data.entity.Sales
import com.example.listacompras.presentation.action.ActionType
import com.example.listacompras.presentation.action.SalesAction
import com.example.listacompras.presentation.activity.SalesDetailActivity
import com.example.listacompras.presentation.adapter.CartListAdapter
import com.example.listacompras.presentation.viewModel.SalesListViewModel
import java.text.SimpleDateFormat
import java.util.Date

class CartFragment : Fragment() {

    private lateinit var tvTotal: TextView

    private val adapter: CartListAdapter by lazy {
        CartListAdapter(::openSalesListDetail)
    }

    private val viewModel: SalesListViewModel by lazy {
        SalesListViewModel.create(requireActivity().application)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_car_shopping, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvCartList: RecyclerView = view.findViewById(R.id.rv_cart)
        val btnFinishCart: Button = view.findViewById(R.id.btn_finish_cart)

        tvTotal = view.findViewById<TextView>(R.id.tv_total)

        btnFinishCart.setOnClickListener {
            deleteSales( ActionType.DELETE_ALL)
        }

        listFromDatabase()
        setupRecyclerView(rvCartList)
    }


    private fun deleteSales(
        actionType: ActionType
    ) {
        val salesAction = SalesAction(null, actionType.name)
        viewModel.execute(salesAction)

    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
    }

    private fun listFromDatabase() {

        val today = convertDate()
        var total = 0f

        // Chame o método getSalesByDate ou qualquer outro método necessário
        viewModel.getSalesByDate(today)

        // Observe as alterações em salesByDateLiveData, se necessário
        viewModel.salesByDateLiveData.observe(viewLifecycleOwner, Observer { salesList ->

            adapter.submitList(salesList)

            // Zere o total antes de calcular novamente
            total = 0f

            // Imprima as vendas no Logcat
            for (sale in salesList) {
                var valueFinal = calculingValueTotal(sale.value, sale.count)
                total += valueFinal
            }

            // Atualize o TextView fora do loop
            tvTotal.text = total.toString()
        })
    }


    private fun calculingValueTotal(valueUnit: String, count: String): Float {
        var totalProduct = valueUnit.toFloat() * count.toInt()
        return totalProduct
    }

    private fun openSalesListDetail(sales: Sales) {
        val intent = SalesDetailActivity.start(requireContext(), sales)
        requireActivity().startActivity(intent)
    }

    fun convertDate(): String {
        val format = SimpleDateFormat("dd/MM/yyyy")
        val date = Date()
        return format.format(date)
    }

    companion object {

        @JvmStatic
        fun newInstance() = CartFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
}