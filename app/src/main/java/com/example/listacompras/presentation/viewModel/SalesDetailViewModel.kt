package com.example.listacompras.presentation.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.listacompras.ListaComprasApplication
import com.example.listacompras.data.dao.SalesDao
import com.example.listacompras.data.entity.Sales
import com.example.listacompras.presentation.action.ActionType
import com.example.listacompras.presentation.action.SalesAction
import kotlinx.coroutines.launch

class SalesDetailViewModel(
    application: Application,
    private val salesDao: SalesDao,
) : ViewModel() {

    val salesLiveData: LiveData<List<Sales>> = salesDao.getSalesActive()

    fun execute(salesAction: SalesAction) {
        when (salesAction.actionType) {
            ActionType.CREATE.name -> insertIntoDatabase(salesAction.sales!!)
//            ActionType.DELETE.name -> deleteIntoTask()
            ActionType.UPDATE.name -> updateIntoTask(salesAction.sales!!)
        }
    }

    //Inserindo uma tarefa no banco de dados
    private fun insertIntoDatabase(sales: Sales) {
        viewModelScope.launch {
            salesDao.insert(sales)
        }
    }

    //Alterando tarefas
    private fun updateIntoTask(sales: Sales) {
        viewModelScope.launch {
            salesDao.update(sales)
        }
    }



    companion object {
        fun getVMFactory(application: Application): ViewModelProvider.Factory {
            val databaseInstance = (application as ListaComprasApplication).getAppDatabase()

            val dao = databaseInstance.salesDao()

            val factory = object : ViewModelProvider.Factory {

                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SalesDetailViewModel(application, dao) as T
                }
            }
            return factory
        }
    }
}