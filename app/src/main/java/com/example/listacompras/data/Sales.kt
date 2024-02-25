package com.example.listacompras.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Sales(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val count: Int,
    val value: String
)
