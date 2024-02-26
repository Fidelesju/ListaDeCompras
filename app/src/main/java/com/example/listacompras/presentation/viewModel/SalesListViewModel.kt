package com.example.listacompras.presentation.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.listacompras.ListaComprasApplication
import com.example.listacompras.data.dao.SalesDao
import com.example.listacompras.data.entity.Sales

class SalesListViewModel(
    private val salesDao: SalesDao
) : ViewModel() {

    val salesListLiveData: LiveData<List<Sales>> = salesDao.getSalesActive()
    var salesByDateLiveData: LiveData<List<Sales>> = MutableLiveData()

    fun getSalesByDate(todayStart: String) {
        salesByDateLiveData = salesDao.getSalesByDate(todayStart)
    }

    companion object {
        fun create(application: Application): SalesListViewModel {
            val databaseInstance = (application as ListaComprasApplication).getAppDatabase()
            val dao = databaseInstance.salesDao()
            return SalesListViewModel(dao)
        }
    }
}