package com.example.listacompras.presentation.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.listacompras.ListaComprasApplication
import com.example.listacompras.data.dao.HistoricSalesDao
import com.example.listacompras.data.entity.HistoricSales

class HistoricSalesListViewModel(
    private val historicSalesDao: HistoricSalesDao
) : ViewModel() {

    val historicSalesListLiveData: LiveData<List<HistoricSales>> =
        historicSalesDao.getAllHistoricSales()


    companion object {
        fun create(application: Application): HistoricSalesListViewModel {
            val databaseInstance = (application as ListaComprasApplication).getAppDatabase()
            val dao = databaseInstance.historicSalesDao()
            return HistoricSalesListViewModel(dao)
        }
    }

}