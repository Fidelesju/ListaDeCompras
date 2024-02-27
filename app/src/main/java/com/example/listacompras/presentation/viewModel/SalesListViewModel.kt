package com.example.listacompras.presentation.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listacompras.ListaComprasApplication
import com.example.listacompras.data.dao.SalesDao
import com.example.listacompras.data.entity.Sales
import com.example.listacompras.presentation.action.ActionType
import com.example.listacompras.presentation.action.SalesAction
import kotlinx.coroutines.launch

class SalesListViewModel(
    private val salesDao: SalesDao
) : ViewModel() {

    val salesListLiveData: LiveData<List<Sales>> = salesDao.getSalesActive()
    var salesByDateLiveData: MutableLiveData<List<Sales>> = MutableLiveData()

    fun execute(salesAction: SalesAction) {
        when (salesAction.actionType) {
            ActionType.DELETE_ALL.name -> deleteIntoTask()
        }
    }

    fun getSalesByDate(todayStart: String) {
        viewModelScope.launch {
            // Obtenha a lista de vendas por data do DAO
            val salesList = salesDao.getSalesByDate(todayStart)

            // Atualize o valor do MutableLiveData com a nova lista
            salesByDateLiveData.postValue(salesList)
        }
    }


    //Deletando uma tarefa
    private fun deleteIntoTask() {
        viewModelScope.launch {
            salesDao.deleteAll()
        }
    }

    companion object {
        fun create(application: Application): SalesListViewModel {
            val databaseInstance = (application as ListaComprasApplication).getAppDatabase()
            val dao = databaseInstance.salesDao()
            return SalesListViewModel(dao)
        }
    }
}