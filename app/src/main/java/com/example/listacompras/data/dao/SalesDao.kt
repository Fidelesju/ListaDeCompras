package com.example.listacompras.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.listacompras.data.entity.Sales

@Dao
interface SalesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sales: Sales)

    @Query("Select * from sales where isActive = 1")
    fun getSalesActive(): LiveData<List<Sales>>

    @Query("SELECT * FROM sales WHERE dateSales >= :todayStart")
    fun getSalesByDate(todayStart: String): LiveData<List<Sales>>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(sales: Sales)
}