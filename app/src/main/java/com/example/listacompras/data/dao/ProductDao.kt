package com.example.listacompras.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.listacompras.data.entity.Products

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(products: Products)

    @Query("Select * from products")
    fun getAll(): LiveData<List<Products>>

    @Delete
    fun delete(products: Products)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(products: Products)

}