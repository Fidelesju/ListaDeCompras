package com.example.listacompras.presentation.action

import com.example.listacompras.data.entity.Sales
import java.io.Serializable


data class SalesAction(
    val sales: Sales?, val actionType: String
) : Serializable