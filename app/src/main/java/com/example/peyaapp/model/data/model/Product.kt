package com.example.peyaapp.model.data.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val image: String,
    val category:String
)
data class ProductListState(
    val loading: Boolean = true,
    val products: List<Product>,
    val error: Exception? = null
)