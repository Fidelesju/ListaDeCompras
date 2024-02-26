package com.example.listacompras.presentation.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.listacompras.ListaComprasApplication
import com.example.listacompras.data.dao.ProductDao
import com.example.listacompras.data.entity.Products

class ProductListViewModel(
    private val productDao: ProductDao
) : ViewModel() {

    val productListLiveData: LiveData<List<Products>> = productDao.getAll()
//    val searchTextLiveData = MutableLiveData<String>()
//
//    fun filterProducts() {
//        val searchText = searchTextLiveData.value.orEmpty()
//        val filteredList = productDao.getFilteredProducts("%$searchText%")
//        searchTextLiveData.value = filteredList.toString()
//    }
    companion object {
        fun create(application: Application): ProductListViewModel {
            val databaseInstance = (application as ListaComprasApplication).getAppDatabase()
            val dao = databaseInstance.productDao()
            return ProductListViewModel(dao)
        }
    }

}