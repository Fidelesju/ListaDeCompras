package com.example.listacompras.presentation.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.listacompras.ListaComprasApplication
import com.example.listacompras.data.dao.HistoricSalesDao
import com.example.listacompras.data.entity.HistoricSales
import com.example.listacompras.presentation.action.ActionType
import com.example.listacompras.presentation.action.HistoricAction
import kotlinx.coroutines.launch

class HistoricSalesDetailViewModel(private val historicSalesDao: HistoricSalesDao) : ViewModel() {

    private val _historicSalesDetailLiveData = MutableLiveData<List<HistoricSales>>()
    val historicSalesDetailLiveData: LiveData<List<HistoricSales>> get() = _historicSalesDetailLiveData

    fun execute(historicAction: HistoricAction) {
        when (historicAction.actionType) {
            ActionType.CREATE.name -> insertIntoDatabase(historicAction.historicSales)
        }
    }

    //Inserindo uma tarefa no banco de dados
    private fun insertIntoDatabase(historicSales: HistoricSales) {
        viewModelScope.launch {
            historicSalesDao.insert(historicSales)
        }
    }

    fun getAllHistoricByDateAndSupermarket(date: String, supermarketing: String) {
        historicSalesDao.getAllHistoricByDateAndSupermarket(date, supermarketing)
            .observeForever { historicSalesList ->
                _historicSalesDetailLiveData.value = historicSalesList
            }
    }


    companion object {
        fun getVMFactory(application: Application): ViewModelProvider.Factory {
            val databaseInstance = (application as ListaComprasApplication).getAppDatabase()
            val dao = databaseInstance.historicSalesDao()
            val factory = object : ViewModelProvider.Factory {

                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HistoricSalesDetailViewModel(dao) as T
                }
            }
            return factory
        }
    }
}
