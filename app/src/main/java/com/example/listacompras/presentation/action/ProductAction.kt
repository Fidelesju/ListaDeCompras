package com.example.listacompras.presentation.action

import com.example.listacompras.data.entity.Products
import java.io.Serializable

//CRUD
enum class ActionType {
    DELETE, UPDATE, CREATE, DELETE_ALL
}

data class ProductAction (
    val products: Products?, val actionType : String
) : Serializable