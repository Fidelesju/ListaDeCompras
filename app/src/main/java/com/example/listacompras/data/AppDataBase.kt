package com.example.listacompras.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.listacompras.data.dao.HistoricSalesDao
import com.example.listacompras.data.dao.ProductDao
import com.example.listacompras.data.dao.SalesDao
import com.example.listacompras.data.entity.HistoricSales
import com.example.listacompras.data.entity.Products
import com.example.listacompras.data.entity.Sales

@Database(entities = [Products::class, Sales::class, HistoricSales::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase(){
    abstract fun productDao() : ProductDao
    abstract  fun salesDao() : SalesDao
    abstract  fun historicSalesDao() : HistoricSalesDao
}