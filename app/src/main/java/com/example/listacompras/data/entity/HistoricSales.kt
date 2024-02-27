package com.example.listacompras.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class HistoricSales(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val count: String,
    val observation: String,
    val value: String,
    val dateSales: String,
    val totalSales: String,
    val superMarketing : String
) : Serializable
