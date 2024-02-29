package com.example.listacompras.presentation.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listacompras.ListaComprasApplication
import com.example.listacompras.data.dao.ProductDao
import com.example.listacompras.data.entity.Products
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val productDao: ProductDao
) : ViewModel() {

    val productListLiveData: LiveData<List<Products>> = productDao.getAll()
    var searchProductsByCategoryOrTitle: MutableLiveData<List<Products>> = MutableLiveData()

    fun searchProducts(searchText: String) {
        viewModelScope.launch {
            // Obtenha a lista de vendas por data do DAO
            val productsList = productDao.searchProducts(searchText)

            // Atualize o valor do MutableLiveData com a nova lista
            searchProductsByCategoryOrTitle.postValue(productsList)
        }
    }

    companion object {
        fun create(application: Application): ProductListViewModel {
            val databaseInstance = (application as ListaComprasApplication).getAppDatabase()
            val dao = databaseInstance.productDao()
            return ProductListViewModel(dao)
        }
    }

}