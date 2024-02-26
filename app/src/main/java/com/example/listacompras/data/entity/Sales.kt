package com.example.listacompras.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity
data class Sales(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val count: String,
    val observation: String,
    val value: String,
    val dateSales : String,
    val isActive : Int
) : Serializable
