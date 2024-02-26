package com.example.listacompras.presentation.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.listacompras.ListaComprasApplication
import com.example.listacompras.data.dao.ProductDao
import com.example.listacompras.data.entity.Products
import com.example.listacompras.presentation.action.ActionType
import com.example.listacompras.presentation.action.ProductAction
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val productDao: ProductDao
) : ViewModel() {


    val productListLiveData: LiveData<List<Products>> = productDao.getAll()

    fun execute(productAction: ProductAction) {
        when (productAction.actionType) {
            ActionType.CREATE.name -> insertIntoDatabase(productAction.products!!)
            ActionType.DELETE.name -> deleteIntoTask(productAction.products!!)
            ActionType.UPDATE.name -> updateIntoTask(productAction.products!!)
        }
    }

    //Inserindo uma tarefa no banco de dados
    private fun insertIntoDatabase(products: Products) {
        viewModelScope.launch {
            productDao.insert(products)
        }
    }

    //Alterando tarefas
    private fun updateIntoTask(products: Products) {
        viewModelScope.launch {
            productDao.update(products)
        }
    }

    //Deletando uma tarefa
    private fun deleteIntoTask(products: Products) {
        viewModelScope.launch {
            productDao.delete(products)
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