package com.example.listacompras.presentation

import com.example.listacompras.data.Products
import java.io.Serializable

//CRUD
enum class ActionType {
    DELETE, UPDATE, CREATE
}

data class ProductsAction(
    val products: Products?, val actionType: String
) : Serializable