package com.oceanx.freshbasket.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oceanx.freshbasket.data.model.Category
import com.oceanx.freshbasket.data.model.Product
import com.oceanx.freshbasket.data.repository.ProductRepository

class HomeViewModel : ViewModel() {

    val categories: MutableLiveData<List<Category>> = MutableLiveData()
    val products: MutableLiveData<List<Product>> = MutableLiveData()
    val popularProducts: MutableLiveData<List<Product>> = MutableLiveData()
    val searchResults: MutableLiveData<List<Product>> = MutableLiveData()
    val selectedCategory: MutableLiveData<String> = MutableLiveData("All")
    val isSearchActive: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        loadCategories()
        loadProducts("All")
        loadPopular()
    }

    private fun loadCategories() {
        categories.value = ProductRepository.categories
    }

    fun loadProducts(category: String) {
        selectedCategory.value = category
        products.value = ProductRepository.getProductsByCategory(category)
    }

    private fun loadPopular() {
        popularProducts.value = ProductRepository.getPopularProducts()
    }

    fun search(query: String) {
        if (query.isBlank()) {
            isSearchActive.value = false
            loadProducts(selectedCategory.value ?: "All")
        } else {
            isSearchActive.value = true
            searchResults.value = ProductRepository.searchProducts(query)
        }
    }
}
