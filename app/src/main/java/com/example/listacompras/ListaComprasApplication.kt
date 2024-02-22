package com.example.listacompras
import android.app.Application
import androidx.room.Room
import com.example.listacompras.data.AppDataBase


class ListaComprasApplication : Application() {

    private lateinit var database: AppDataBase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext, AppDataBase::class.java, "taskbeats-database"
        ).build()
    }

    fun getAppDatabase(): AppDataBase {
        return database
    }
}