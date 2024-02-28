package com.example.listacompras.presentation.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompras.R
import com.example.listacompras.data.entity.HistoricSales
import com.example.listacompras.data.entity.Sales
import com.example.listacompras.presentation.action.ActionType
import com.example.listacompras.presentation.action.HistoricAction
import com.example.listacompras.presentation.action.SalesAction
import com.example.listacompras.presentation.activity.SalesDetailActivity
import com.example.listacompras.presentation.adapter.CartListAdapter
import com.example.listacompras.presentation.viewModel.HistoricSalesDetailViewModel
import com.example.listacompras.presentation.viewModel.SalesListViewModel
import java.text.SimpleDateFormat
import java.util.Date

class CartFragment : Fragment() {

    private lateinit var tvTotal: TextView
    private lateinit var ctnContent: LinearLayout
    private lateinit var ctnCart: ConstraintLayout
    private val adapter: CartListAdapter by lazy {
        CartListAdapter(::openSalesListDetail)
    }

    private val viewModel: SalesListViewModel by lazy {
        SalesListViewModel.create(requireActivity().application)
    }

    private var supermarketing: String = ""


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
        ctnContent = view.findViewById(R.id.ctn_content)
        ctnCart = view.findViewById(R.id.ctn_cart)
        btnFinishCart.setOnClickListener {
            deleteSales(ActionType.DELETE_ALL)
            showPopupCount(requireContext())
        }

        listFromDatabase()
        setupRecyclerView(rvCartList)
    }

    override fun onStart() {
        super.onStart()
    }


    private fun deleteSales(actionType: ActionType) {
        val salesAction = SalesAction(null, actionType.name)
        viewModel.execute(salesAction)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
    }

    private fun listFromDatabase() {
        val today = convertDate()

        // Chame o método getSalesByDate ou qualquer outro método necessário
        viewModel.getSalesByDate(today)

        // Observe as alterações em salesByDateLiveData, se necessário
        viewModel.salesByDateLiveData.observe(viewLifecycleOwner, Observer { salesList ->
            if (salesList.isEmpty()) {
                ctnContent.visibility = View.VISIBLE
                ctnCart.visibility = View.GONE
            } else {
                ctnContent.visibility = View.GONE
                ctnCart.visibility = View.VISIBLE
            }
            adapter.submitList(salesList)

            // Zere o total antes de calcular novamente
            var total = 0f

            // Imprima as vendas no Logcat
            for (sale in salesList) {
                val valueFinal = calculatingValueTotal(sale.value, sale.count)
                total += valueFinal

            }

            // Atualize o TextView fora do loop
            tvTotal.text = total.toString()


        })
    }

    private fun insertHistoric(supermarketing : String) {
        val today = convertDate()

        // Chame o método getSalesByDate ou qualquer outro método necessário
        viewModel.getSalesByDate(today)

        // Observe as alterações em salesByDateLiveData, se necessário
        viewModel.salesByDateLiveData.observe(viewLifecycleOwner, Observer { salesList ->
            adapter.submitList(salesList)

            // Zere o total antes de calcular novamente
            var total = 0f

            // Crie uma lista para armazenar os históricos
            val historicList = mutableListOf<HistoricSales>()

            // Imprima as vendas no Logcat
            for (sale in salesList) {
                val valueFinal = calculatingValueTotal(sale.value, sale.count)
                total += valueFinal

                // Crie o objeto HistoricSales, mas não execute a ação aqui
                val historicSales = HistoricSales(
                    0,
                    sale.title,
                    sale.count,
                    sale.observation,
                    sale.value,
                    sale.dateSales,
                    total.toString(),
                    supermarketing
                )

                // Adicione o histórico à lista
                historicList.add(historicSales)
            }

            // Atualize o TextView fora do loop
            tvTotal.text = total.toString()

            // Agora, execute a ação para criar todos os históricos
            historicList.forEach { historicSales ->
                val historicViewModel = ViewModelProvider(
                    requireActivity(),
                    HistoricSalesDetailViewModel.getVMFactory(requireActivity().application)
                ).get(HistoricSalesDetailViewModel::class.java)
                historicViewModel.execute(HistoricAction(historicSales, ActionType.CREATE.name))
            }
        })
    }

    private fun calculatingValueTotal(valueUnit: String, count: String): Float {
        return valueUnit.toFloat() * count.toFloat()
    }

    private fun openSalesListDetail(sales: Sales) {
        val intent = SalesDetailActivity.start(requireContext(), sales)
        requireActivity().startActivity(intent)
    }

    private fun convertDate(): String {
        val format = SimpleDateFormat("dd/MM/yyyy")
        val date = Date()
        return format.format(date)
    }

    private fun showPopupCount(context: Context) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_supermarketing, null)
        val editTextQuantidade = dialogView.findViewById<EditText>(R.id.editTextSupermarketing)

        builder.setView(dialogView)
            .setTitle("Digite o nome do supermercado")
            .setPositiveButton("OK") { dialog, _ ->
                supermarketing = editTextQuantidade.text.toString()
                if (supermarketing.isNotEmpty()) {
                   insertHistoric(supermarketing)
                } else {
                    Toast.makeText(
                        context,
                        "Digite um supermercado válido",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CartFragment().apply {
            arguments = Bundle().apply {
                // Se precisar de argumentos, pode configurar aqui
            }
        }
    }
}
