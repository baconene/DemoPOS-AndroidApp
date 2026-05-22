package com.demopos.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demopos.data.local.converters.DateConverter
import com.demopos.data.local.dao.*
import com.demopos.data.local.entities.*

@Database(
    entities = [
        UserEntity::class,
        AuthSessionEntity::class,
        ProductEntity::class,
        CategoryEntity::class,
        InventoryEntity::class,
        OrderEntity::class,
        OrderItemEntity::class,
        PaymentEntity::class,
        CustomerEntity::class,
        ReceiptEntity::class,
        SyncQueueEntity::class,
        ActivityLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class DemoPOSDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun authSessionDao(): AuthSessionDao
    abstract fun productDao(): ProductDao
    abstract fun categoryDao(): CategoryDao
    abstract fun inventoryDao(): InventoryDao
    abstract fun orderDao(): OrderDao
    abstract fun orderItemDao(): OrderItemDao
    abstract fun paymentDao(): PaymentDao
    abstract fun customerDao(): CustomerDao
    abstract fun receiptDao(): ReceiptDao
    abstract fun syncQueueDao(): SyncQueueDao
    abstract fun activityLogDao(): ActivityLogDao
}
