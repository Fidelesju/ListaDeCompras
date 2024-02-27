package com.example.listacompras.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompras.R
import com.example.listacompras.data.entity.HistoricSales
import com.example.listacompras.presentation.activity.HistoricSalesDetailActivity
import com.example.listacompras.presentation.adapter.HistoricListAdapter
import com.example.listacompras.presentation.viewModel.HistoricSalesListViewModel

class HistoricSalesFragment : Fragment() {
//    private lateinit var ctnContent: LinearLayout
    private lateinit var edtSearch: EditText

    private val adapter: HistoricListAdapter by lazy {

        HistoricListAdapter(::openHistoricListDetail)
    }
    private val viewModel: HistoricSalesListViewModel by lazy {
        HistoricSalesListViewModel.create(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historic_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvProductList: RecyclerView = view.findViewById(R.id.rv_historic)

        listFromDatabase()
        setupRecyclerView(rvProductList)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
    }

    private fun listFromDatabase() {

        val listObserver = Observer<List<HistoricSales>> {
//            if (it.isEmpty()) {
//                ctnContent.visibility = View.VISIBLE
//                edtSearch.visibility = View.GONE
//            } else {
//                ctnContent.visibility = View.GONE
//                edtSearch.visibility = View.VISIBLE
//            }
            adapter.submitList(it)
        }

        //LiveData
        viewModel.historicSalesListLiveData.observe(viewLifecycleOwner, listObserver)
    }

    private fun openHistoricListDetail(historicSales: HistoricSales) {
        val intent = HistoricSalesDetailActivity.start(requireContext(), historicSales)
        requireActivity().startActivity(intent)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            HistoricSalesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}