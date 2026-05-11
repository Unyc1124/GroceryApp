package com.oceanx.freshbasket.viewmodel

import androidx.lifecycle.*
import com.oceanx.freshbasket.data.local.entity.CartItemEntity
import com.oceanx.freshbasket.data.model.Product
import com.oceanx.freshbasket.data.repository.CartRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CartViewModel(private val repository: CartRepository) : ViewModel() {

    val cartItems: StateFlow<List<CartItemEntity>> = repository.cartItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cartCount: StateFlow<Int> = repository.cartCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val cartTotal: StateFlow<Double?> = repository.cartTotal
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    private val _addToCartEvent = MutableLiveData<String>()
    val addToCartEvent: LiveData<String> = _addToCartEvent

    fun addToCart(product: Product) {
        viewModelScope.launch {
            repository.addToCart(product)
            _addToCartEvent.value = "${product.name} added to cart"
        }
    }

    fun increaseQty(item: CartItemEntity) {
        viewModelScope.launch { repository.increaseQty(item) }
    }

    fun decreaseQty(item: CartItemEntity) {
        viewModelScope.launch { repository.decreaseQty(item) }
    }

    fun removeItem(item: CartItemEntity) {
        viewModelScope.launch { repository.removeItem(item) }
    }

    fun clearCart() {
        viewModelScope.launch { repository.clearCart() }
    }

    fun getDeliveryFee(total: Double): Double = if (total >= 500) 0.0 else 40.0

    fun getGrandTotal(subtotal: Double): Double = subtotal + getDeliveryFee(subtotal)
}

class CartViewModelFactory(private val repository: CartRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
