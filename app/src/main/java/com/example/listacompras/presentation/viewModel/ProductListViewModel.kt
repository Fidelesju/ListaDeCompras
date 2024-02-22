package com.example.listacompras.presentation.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.listacompras.ListaComprasApplication
import com.example.listacompras.data.ProductDao
import com.example.listacompras.data.Products

class ProductListViewModel(
    productDao: ProductDao
) : ViewModel() {

    val productListLiveData: LiveData<List<Products>> = productDao.getAll()

    companion object {
        fun create(application: Application): ProductListViewModel {
            val databaseInstance = (application as ListaComprasApplication).getAppDatabase()
            val dao = databaseInstance.productDao()
            return ProductListViewModel(dao)
        }
    }
}