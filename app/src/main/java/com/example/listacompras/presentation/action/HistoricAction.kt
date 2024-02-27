package com.example.listacompras.presentation.action

import com.example.listacompras.data.entity.HistoricSales
import com.example.listacompras.data.entity.Products
import java.io.Serializable

data class HistoricAction (
    val historicSales: HistoricSales, val actionType : String
) : Serializable