package com.demopos.data.local.dao

import androidx.room.*
import com.demopos.data.local.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    fun getUserByEmailFlow(email: String): Flow<UserEntity?>
}

@Dao
interface AuthSessionDao {
    @Insert
    suspend fun insertSession(session: AuthSessionEntity)

    @Update
    suspend fun updateSession(session: AuthSessionEntity)

    @Query("SELECT * FROM auth_sessions WHERE id = :sessionId")
    suspend fun getSessionById(sessionId: String): AuthSessionEntity?

    @Query("SELECT * FROM auth_sessions WHERE userId = :userId AND isActive = 1 LIMIT 1")
    suspend fun getActiveSessionForUser(userId: String): AuthSessionEntity?

    @Query("UPDATE auth_sessions SET isActive = 0 WHERE userId = :userId")
    suspend fun invalidateUserSessions(userId: String)
}

@Dao
interface ProductDao {
    @Insert
    suspend fun insertProduct(product: ProductEntity)

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProductById(productId: String): ProductEntity?

    @Query("SELECT * FROM products WHERE categoryId = :categoryId ORDER BY name ASC")
    fun getProductsByCategory(categoryId: String): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavoriteProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchProducts(query: String): Flow<List<ProductEntity>>

    @Query("UPDATE products SET isFavorite = :isFavorite WHERE id = :productId")
    suspend fun updateProductFavorite(productId: String, isFavorite: Boolean)
}

@Dao
interface CategoryDao {
    @Insert
    suspend fun insertCategory(category: CategoryEntity)

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Query("SELECT * FROM categories WHERE isActive = 1 ORDER BY displayOrder ASC")
    fun getActiveCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories WHERE id = :categoryId")
    suspend fun getCategoryById(categoryId: String): CategoryEntity?
}

@Dao
interface InventoryDao {
    @Insert
    suspend fun insertInventory(inventory: InventoryEntity)

    @Update
    suspend fun updateInventory(inventory: InventoryEntity)

    @Query("SELECT * FROM inventory WHERE productId = :productId")
    suspend fun getInventoryByProductId(productId: String): InventoryEntity?

    @Query("SELECT * FROM inventory WHERE quantity <= minThreshold")
    fun getLowStockItems(): Flow<List<InventoryEntity>>

    @Query("UPDATE inventory SET quantity = quantity - :amount WHERE productId = :productId")
    suspend fun decrementStock(productId: String, amount: Int)

    @Query("UPDATE inventory SET quantity = quantity + :amount WHERE productId = :productId")
    suspend fun incrementStock(productId: String, amount: Int)
}

@Dao
interface OrderDao {
    @Insert
    suspend fun insertOrder(order: OrderEntity): Long

    @Update
    suspend fun updateOrder(order: OrderEntity)

    @Query("SELECT * FROM orders WHERE id = :orderId")
    suspend fun getOrderById(orderId: String): OrderEntity?

    @Query("SELECT * FROM orders ORDER BY createdAt DESC LIMIT :limit OFFSET :offset")
    fun getOrders(limit: Int, offset: Int): Flow<List<OrderEntity>>

    @Query("SELECT * FROM orders WHERE status = :status ORDER BY createdAt DESC")
    fun getOrdersByStatus(status: String): Flow<List<OrderEntity>>

    @Query("SELECT SUM(totalAmount) FROM orders WHERE DATE(createdAt) = DATE(:date)")
    suspend fun getDailySales(date: Long): Double?

    @Query("SELECT COUNT(*) FROM orders WHERE DATE(createdAt) = DATE(:date)")
    suspend fun getDailyOrderCount(date: Long): Int
}

@Dao
interface OrderItemDao {
    @Insert
    suspend fun insertOrderItem(orderItem: OrderItemEntity)

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    suspend fun getOrderItems(orderId: String): List<OrderItemEntity>

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    fun getOrderItemsFlow(orderId: String): Flow<List<OrderItemEntity>>
}

@Dao
interface PaymentDao {
    @Insert
    suspend fun insertPayment(payment: PaymentEntity)

    @Update
    suspend fun updatePayment(payment: PaymentEntity)

    @Query("SELECT * FROM payments WHERE orderId = :orderId")
    suspend fun getPaymentsByOrderId(orderId: String): List<PaymentEntity>

    @Query("SELECT SUM(amount) FROM payments WHERE method = :method AND DATE(createdAt) = DATE(:date)")
    suspend fun getDailyPaymentByMethod(method: String, date: Long): Double?
}

@Dao
interface CustomerDao {
    @Insert
    suspend fun insertCustomer(customer: CustomerEntity)

    @Update
    suspend fun updateCustomer(customer: CustomerEntity)

    @Query("SELECT * FROM customers WHERE id = :customerId")
    suspend fun getCustomerById(customerId: String): CustomerEntity?

    @Query("SELECT * FROM customers WHERE name LIKE '%' || :query || '%' OR phoneNumber LIKE '%' || :query || '%'")
    fun searchCustomers(query: String): Flow<List<CustomerEntity>>

    @Query("SELECT * FROM customers ORDER BY totalSpent DESC LIMIT :limit")
    fun getTopCustomers(limit: Int): Flow<List<CustomerEntity>>
}

@Dao
interface ReceiptDao {
    @Insert
    suspend fun insertReceipt(receipt: ReceiptEntity)

    @Query("SELECT * FROM receipts WHERE id = :receiptId")
    suspend fun getReceiptById(receiptId: String): ReceiptEntity?

    @Query("SELECT * FROM receipts WHERE orderId = :orderId")
    suspend fun getReceiptByOrderId(orderId: String): ReceiptEntity?
}

@Dao
interface SyncQueueDao {
    @Insert
    suspend fun insertSyncItem(item: SyncQueueEntity)

    @Update
    suspend fun updateSyncItem(item: SyncQueueEntity)

    @Query("SELECT * FROM sync_queue WHERE status = 'PENDING' ORDER BY createdAt ASC")
    suspend fun getPendingSyncItems(): List<SyncQueueEntity>

    @Query("DELETE FROM sync_queue WHERE id = :itemId")
    suspend fun removeSyncItem(itemId: String)

    @Query("SELECT COUNT(*) FROM sync_queue WHERE status = 'PENDING'")
    fun getPendingItemCount(): Flow<Int>
}

@Dao
interface ActivityLogDao {
    @Insert
    suspend fun insertActivityLog(log: ActivityLogEntity)

    @Query("SELECT * FROM activity_logs WHERE userId = :userId ORDER BY createdAt DESC LIMIT :limit")
    fun getActivityLogs(userId: String, limit: Int): Flow<List<ActivityLogEntity>>
}
