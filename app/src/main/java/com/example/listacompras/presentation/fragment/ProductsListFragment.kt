package com.example.listacompras.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.listacompras.R
import com.example.listacompras.data.Products
import com.example.listacompras.presentation.activity.ProductDetailActivity
import com.example.listacompras.presentation.adapter.ProductListAdapter
import com.example.listacompras.presentation.viewModel.ProductListViewModel

class ProductsListFragment : Fragment() {

    //Conteiner de imagem em estado de vazio
    private lateinit var ctnContent: LinearLayout

    //Adapter
    private val adapter: ProductListAdapter by lazy {
        ProductListAdapter(::openProductListDetail)
    }

    //View Model
    private val viewModel: ProductListViewModel by lazy {
        ProductListViewModel.create(requireActivity().application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ctnContent = view.findViewById(R.id.ctn_content)
        val rvTaskList: RecyclerView = view.findViewById(R.id.rv_product_list)

        rvTaskList.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        listFromDatabase()
    }

    //Listando as tarefas na tela
    private fun listFromDatabase() {

        //Observer
        val listObserver = Observer<List<Products>> {
            if (it.isEmpty()) {
                ctnContent.visibility = View.VISIBLE
            } else {
                ctnContent.visibility = View.GONE
            }
            adapter.submitList(it)
        }

        //LiveData
        viewModel.productListLiveData.observe(this, listObserver)

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ProductsListFragment().apply {

            }
    }

    //Evento de clique vindo do botão (nao contem tarefa)
    private fun openProductListDetail(products: Products) {
        val intent = ProductDetailActivity.start(requireContext(), products)
        requireActivity().startActivity(intent)
    }

}