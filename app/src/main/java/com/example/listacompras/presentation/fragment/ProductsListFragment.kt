package com.example.listacompras.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompras.R
import com.example.listacompras.data.ProductDao
import com.example.listacompras.data.Products
import com.example.listacompras.presentation.activity.ProductDetailActivity
import com.example.listacompras.presentation.adapter.ProductListAdapter
import com.example.listacompras.presentation.viewModel.ProductListViewModel

class ProductsListFragment : Fragment() {

    private lateinit var ctnContent: LinearLayout

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
        val rvTaskList: RecyclerView = view.findViewById(R.id.rv_product_list)
        listFromDatabase()
        rvTaskList.adapter = adapter

    }

    private fun listFromDatabase() {

        val test = Observer<List<Products>> {
            Log.d("Observer", "onChanged called with data: $it")
            adapter.submitList(it)
        }
        viewModel.productListLiveData.observe(viewLifecycleOwner, test)
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
