package com.example.listacompras.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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

    companion object {
        private const val DATABASE_NAME = "listacompras-database"

        fun buildDatabase(context: Context): AppDataBase {
            return Room.databaseBuilder(context, AppDataBase::class.java, DATABASE_NAME)
                .addMigrations(MIGRATION_1_2) // Adicione suas migrações aqui
                .build()
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Adicione suas operações de migração aqui
            }
        }
    }
}