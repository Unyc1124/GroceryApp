package com.oceanx.freshbasket.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val originalPrice: Double,
    val imageRes: Int,
    val category: String,
    val unit: String,
    val rating: Float,
    val isPopular: Boolean = false,
    val discount: Int = 0
) : Parcelable
