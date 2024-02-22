package com.example.listacompras.presentation.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.listacompras.ListaComprasApplication
import com.example.listacompras.data.ProductDao
import com.example.listacompras.data.Products
import com.example.listacompras.presentation.ActionType
import com.example.listacompras.presentation.ProductsAction
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val productDao: ProductDao
) : ViewModel() {

    val productListLiveData: LiveData<List<Products>> = productDao.getAll()

    fun execute(producAction: ProductsAction) {
        when (producAction.actionType) {
            ActionType.CREATE.name -> insertIntoDatabase(producAction.products!!)
            ActionType.DELETE.name -> deleteIntoProducts(producAction.products!!)
            ActionType.UPDATE.name -> updateIntoProducts(producAction.products!!)
        }
    }

    //Inserindo uma tarefa no banco de dados
    private fun insertIntoDatabase(product: Products) {
        viewModelScope.launch {
            productDao.insert(product)
        }
    }

    //Alterando tarefas
    private fun updateIntoProducts(product: Products) {
        viewModelScope.launch {
            productDao.update(product)
        }
    }

    //Deletando uma tarefa
    private fun deleteIntoProducts(product: Products) {
        viewModelScope.launch {
            productDao.delete(product)
        }
    }

    companion object {
        fun getVMFactory(application: Application): ViewModelProvider.Factory {
            val databaseInstance = (application as ListaComprasApplication).getAppDatabase()

            val dao = databaseInstance.productDao()

            val factory = object : ViewModelProvider.Factory {

                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProductDetailViewModel(dao) as T
                }
            }
            return factory
        }
    }
}