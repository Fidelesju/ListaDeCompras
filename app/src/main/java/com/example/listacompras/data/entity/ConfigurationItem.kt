package com.example.listacompras.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class ConfigurationItem(
    val title: String
) : Serializable
