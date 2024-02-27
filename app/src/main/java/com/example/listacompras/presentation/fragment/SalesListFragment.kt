package com.example.listacompras.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompras.R
import com.example.listacompras.data.entity.Sales
import com.example.listacompras.presentation.activity.SalesDetailActivity
import com.example.listacompras.presentation.adapter.SalesAdapter
import com.example.listacompras.presentation.viewModel.SalesListViewModel

class SalesListFragment : Fragment() {
    private lateinit var ctnContent: LinearLayout

    private val adapter: SalesAdapter by lazy {
        SalesAdapter(::openSalesListDetail)
    }

    private val viewModel: SalesListViewModel by lazy {
        SalesListViewModel.create(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sales, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvTaskList: RecyclerView = view.findViewById(R.id.rv_sales)
        ctnContent = view.findViewById(R.id.ctn_content)

        listFromDatabase()
        setupRecyclerView(rvTaskList)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter

    }

    private fun listFromDatabase() {

        val listObserver = Observer<List<Sales>> {
            if (it.isEmpty()) {
                ctnContent.visibility = View.VISIBLE
            } else {
                ctnContent.visibility = View.GONE
            }
            adapter.submitList(it)
        }

        //LiveData
        viewModel.salesListLiveData.observe(viewLifecycleOwner, listObserver)
    }

    private fun openSalesListDetail(sales: Sales) {
        val intent = SalesDetailActivity.start(requireContext(), sales)
        requireActivity().startActivity(intent)
    }



    companion object {

        @JvmStatic
        fun newInstance() =
            SalesListFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}