package com.oceanx.freshbasket.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey
    val productId: Int,
    val name: String,
    val price: Double,
    val imageRes: Int,
    val unit: String,
    var quantity: Int = 1
)
