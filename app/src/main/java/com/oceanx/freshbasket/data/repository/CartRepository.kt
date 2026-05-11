package com.oceanx.freshbasket.data.repository

import com.oceanx.freshbasket.data.local.dao.CartDao
import com.oceanx.freshbasket.data.local.entity.CartItemEntity
import com.oceanx.freshbasket.data.model.Product
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {

    val cartItems: Flow<List<CartItemEntity>> = cartDao.getAllCartItems()
    val cartCount: Flow<Int> = cartDao.getCartCount()
    val cartTotal: Flow<Double?> = cartDao.getCartTotal()

    suspend fun addToCart(product: Product) {
        val existing = cartDao.getCartItem(product.id)
        if (existing != null) {
            cartDao.updateCartItem(existing.copy(quantity = existing.quantity + 1))
        } else {
            cartDao.insertCartItem(
                CartItemEntity(
                    productId = product.id,
                    name = product.name,
                    price = product.price,
                    imageRes = product.imageRes,
                    unit = product.unit,
                    quantity = 1
                )
            )
        }
    }

    suspend fun increaseQty(item: CartItemEntity) {
        cartDao.updateCartItem(item.copy(quantity = item.quantity + 1))
    }

    suspend fun decreaseQty(item: CartItemEntity) {
        if (item.quantity <= 1) {
            cartDao.deleteCartItem(item)
        } else {
            cartDao.updateCartItem(item.copy(quantity = item.quantity - 1))
        }
    }

    suspend fun removeItem(item: CartItemEntity) {
        cartDao.deleteCartItem(item)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }

    suspend fun getCartItem(productId: Int): CartItemEntity? {
        return cartDao.getCartItem(productId)
    }
}
