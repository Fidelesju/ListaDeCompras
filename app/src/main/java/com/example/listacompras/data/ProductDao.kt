package com.example.listacompras.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Products)

    @Query("SELECT * FROM Products")
    fun getAll(): LiveData<List<Products>>

    @Delete
    suspend fun delete(product: Products)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(product: Products)

}