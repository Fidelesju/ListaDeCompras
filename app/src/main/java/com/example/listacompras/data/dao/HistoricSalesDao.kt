package com.example.listacompras.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.listacompras.data.entity.HistoricSales

@Dao
interface HistoricSalesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(historicSales: HistoricSales)

    @Query("SELECT DISTINCT datesales, MAX(id) as id, title, count, observation, count, datesales, totalSales, superMarketing, value FROM historicsales GROUP BY datesales, superMarketing ORDER BY datesales DESC")
    fun getAllHistoricSales(): LiveData<List<HistoricSales>>

    @Query("SELECT * FROM historicsales WHERE datesales = :date AND superMarketing = :supermarket ORDER BY datesales DESC")
    fun getAllHistoricByDateAndSupermarket(date: String, supermarket: String): LiveData<List<HistoricSales>>

}