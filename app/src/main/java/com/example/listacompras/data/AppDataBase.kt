package com.example.listacompras.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Products::class, Sales::class], version = 2, exportSchema = false)
abstract class AppDataBase : RoomDatabase(){
    abstract fun productDao() : ProductDao
    abstract  fun salesDao() : SalesDao
}