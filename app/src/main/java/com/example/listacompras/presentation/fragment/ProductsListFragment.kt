package com.example.listacompras.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompras.R
import com.example.listacompras.data.entity.Products
import com.example.listacompras.presentation.activity.ProductDetailActivity
import com.example.listacompras.presentation.adapter.ProductListAdapter
import com.example.listacompras.presentation.viewModel.ProductListViewModel

class ProductsListFragment : Fragment() {

    private lateinit var ctnContent: LinearLayout
    private lateinit var edtSearch: EditText
    private val adapter: ProductListAdapter by lazy {
        ProductListAdapter(::openProductListDetail)
    }
    private val viewModel: ProductListViewModel by lazy {
        ProductListViewModel.create(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_products_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ctnContent = view.findViewById(R.id.ctn_content)
        edtSearch = view.findViewById(R.id.edt_search)
        val rvProductList: RecyclerView = view.findViewById(R.id.rv_product_list)
        val search = edtSearch.text.toString()

        listFromDatabase()
        setupRecyclerView(rvProductList)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
    }

    private fun listFromDatabase() {

        val listObserver = Observer<List<Products>> {
            if (it.isEmpty()) {
                ctnContent.visibility = View.VISIBLE
                edtSearch.visibility = View.GONE
            } else {
                ctnContent.visibility = View.GONE
                edtSearch.visibility = View.VISIBLE
            }
            adapter.submitList(it)
        }

        //LiveData
        viewModel.productListLiveData.observe(viewLifecycleOwner, listObserver)
    }


    private fun openProductListDetail(products: Products) {
        val intent = ProductDetailActivity.start(requireContext(), products)
        requireActivity().startActivity(intent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProductsListFragment().apply {}
    }
}
