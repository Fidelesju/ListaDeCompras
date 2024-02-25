package com.example.listacompras.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SalesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert (sales: Sales)
}