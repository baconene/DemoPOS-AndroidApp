package com.demopos.domain.repositories

import com.demopos.domain.entities.Product
import com.demopos.domain.entities.Category
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProductsByCategory(categoryId: String): Flow<List<Product>>
    fun getFavoriteProducts(): Flow<List<Product>>
    fun searchProducts(query: String): Flow<List<Product>>
    suspend fun getProductById(productId: String): Product?
    suspend fun updateProductFavorite(productId: String, isFavorite: Boolean): Result<Unit>
    fun getActiveCategories(): Flow<List<Category>>
}

interface InventoryRepository {
    suspend fun getStockQuantity(productId: String): Int?
    fun getLowStockItems(): Flow<List<Product>>
    suspend fun updateStock(productId: String, quantity: Int): Result<Unit>
}

interface OrderRepository {
    suspend fun createOrder(order: com.demopos.domain.entities.Order): Result<String>
    suspend fun getOrderById(orderId: String): com.demopos.domain.entities.Order?
    fun getOrders(limit: Int, offset: Int): Flow<List<com.demopos.domain.entities.Order>>
    suspend fun updateOrderStatus(orderId: String, status: String): Result<Unit>
    suspend fun getDailySales(date: Long): Double?
    suspend fun getDailyOrderCount(date: Long): Int
}
