package com.example.peyaapp.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="products")
data class ProductEntity (
    @PrimaryKey val id : String,
    val name : String,
    val price: Double = 0.0,
    val description: String = "",
    val image: String,
    val category: String = "General",
)