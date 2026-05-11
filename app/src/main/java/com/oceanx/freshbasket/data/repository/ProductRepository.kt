package com.oceanx.freshbasket.data.repository

import com.oceanx.freshbasket.R
import com.oceanx.freshbasket.data.model.Category
import com.oceanx.freshbasket.data.model.Product

object ProductRepository {

    val categories = listOf(
        Category(1, "All", "🛒", "#4CAF50"),
        Category(2, "Fruits", "🍎", "#FF5722"),
        Category(3, "Vegetables", "🥦", "#4CAF50"),
        Category(4, "Dairy", "🥛", "#2196F3"),
        Category(5, "Bakery", "🍞", "#FF9800"),
        Category(6, "Beverages", "🧃", "#9C27B0"),
        Category(7, "Snacks", "🍿", "#F44336"),
        Category(8, "Meat", "🥩", "#795548")
    )

    val allProducts = listOf(
        // Fruits
        Product(1, "Fresh Apples", 129.0, 160.0, R.drawable.ic_apple, "Fruits", "1 kg", 4.5f, true, 19),
        Product(2, "Ripe Bananas", 49.0, 60.0, R.drawable.ic_banana, "Fruits", "500 g", 4.3f, true, 18),
        Product(3, "Juicy Mangoes", 89.0, 120.0, R.drawable.ic_mango, "Fruits", "500 g", 4.7f, true, 26),
        Product(4, "Sweet Grapes", 149.0, 180.0, R.drawable.ic_grapes, "Fruits", "500 g", 4.4f, false, 17),
        Product(5, "Watermelon", 79.0, 90.0, R.drawable.ic_watermelon, "Fruits", "1 pc", 4.2f, false, 12),
        Product(6, "Oranges", 99.0, 120.0, R.drawable.ic_orange, "Fruits", "1 kg", 4.3f, true, 17),

        // Vegetables
        Product(7, "Spinach", 29.0, 40.0, R.drawable.ic_spinach, "Vegetables", "250 g", 4.5f, true, 27),
        Product(8, "Tomatoes", 39.0, 50.0, R.drawable.ic_tomato, "Vegetables", "500 g", 4.4f, true, 22),
        Product(9, "Broccoli", 59.0, 80.0, R.drawable.ic_broccoli, "Vegetables", "500 g", 4.6f, false, 26),
        Product(10, "Carrots", 35.0, 45.0, R.drawable.ic_carrot, "Vegetables", "500 g", 4.3f, true, 22),
        Product(11, "Bell Peppers", 69.0, 90.0, R.drawable.ic_pepper, "Vegetables", "250 g", 4.5f, false, 23),
        Product(12, "Onions", 25.0, 35.0, R.drawable.ic_onion, "Vegetables", "1 kg", 4.1f, true, 28),

        // Dairy
        Product(13, "Full Cream Milk", 68.0, 78.0, R.drawable.ic_milk, "Dairy", "1 L", 4.6f, true, 12),
        Product(14, "Cheddar Cheese", 189.0, 220.0, R.drawable.ic_cheese, "Dairy", "200 g", 4.5f, true, 14),
        Product(15, "Fresh Yogurt", 55.0, 70.0, R.drawable.ic_yogurt, "Dairy", "400 g", 4.4f, false, 21),
        Product(16, "Butter", 55.0, 65.0, R.drawable.ic_butter, "Dairy", "100 g", 4.3f, true, 15),

        // Bakery
        Product(17, "Whole Wheat Bread", 45.0, 55.0, R.drawable.ic_bread, "Bakery", "400 g", 4.4f, true, 18),
        Product(18, "Croissants", 89.0, 110.0, R.drawable.ic_croissant, "Bakery", "4 pcs", 4.7f, true, 19),
        Product(19, "Multigrain Buns", 35.0, 45.0, R.drawable.ic_bun, "Bakery", "6 pcs", 4.2f, false, 22),

        // Beverages
        Product(20, "Orange Juice", 99.0, 120.0, R.drawable.ic_juice, "Beverages", "1 L", 4.5f, true, 17),
        Product(21, "Green Tea", 149.0, 180.0, R.drawable.ic_tea, "Beverages", "25 bags", 4.6f, false, 17),
        Product(22, "Sparkling Water", 45.0, 55.0, R.drawable.ic_water, "Beverages", "1 L", 4.3f, true, 18),

        // Snacks
        Product(23, "Mixed Nuts", 199.0, 250.0, R.drawable.ic_nuts, "Snacks", "200 g", 4.7f, true, 20),
        Product(24, "Dark Chocolate", 129.0, 160.0, R.drawable.ic_chocolate, "Snacks", "100 g", 4.8f, true, 19),
        Product(25, "Popcorn", 49.0, 60.0, R.drawable.ic_popcorn, "Snacks", "100 g", 4.2f, false, 18),

        // Meat
        Product(26, "Chicken Breast", 249.0, 299.0, R.drawable.ic_chicken, "Meat", "500 g", 4.5f, true, 16),
        Product(27, "Eggs", 89.0, 105.0, R.drawable.ic_egg, "Meat", "12 pcs", 4.6f, true, 15),
        Product(28, "Salmon Fillet", 399.0, 480.0, R.drawable.ic_fish, "Meat", "300 g", 4.7f, false, 16)
    )

    fun getProductsByCategory(category: String): List<Product> {
        return if (category == "All") allProducts
        else allProducts.filter { it.category == category }
    }

    fun searchProducts(query: String): List<Product> {
        return allProducts.filter { it.name.contains(query, ignoreCase = true) }
    }

    fun getPopularProducts(): List<Product> = allProducts.filter { it.isPopular }
}
