package com.demopos.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val email: String,
    val name: String,
    val pin: String?,
    val role: String, // ADMIN, CASHIER, MANAGER
    val isActive: Boolean = true,
    val createdAt: Date,
    val updatedAt: Date
)

@Entity(tableName = "auth_sessions")
data class AuthSessionEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val token: String,
    val refreshToken: String?,
    val expiresAt: Date,
    val createdAt: Date,
    val isActive: Boolean = true
)

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String? = null,
    val imageUrl: String? = null,
    val displayOrder: Int = 0,
    val isActive: Boolean = true,
    val createdAt: Date,
    val updatedAt: Date
)

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey
    val id: String,
    val categoryId: String,
    val name: String,
    val description: String? = null,
    val price: Double,
    val cost: Double? = null,
    val barcode: String? = null,
    val imageUrl: String? = null,
    val isFavorite: Boolean = false,
    val isActive: Boolean = true,
    val createdAt: Date,
    val updatedAt: Date
)

@Entity(tableName = "inventory")
data class InventoryEntity(
    @PrimaryKey
    val id: String,
    val productId: String,
    val quantity: Int,
    val minThreshold: Int = 10,
    val lastRestockDate: Date? = null,
    val createdAt: Date,
    val updatedAt: Date
)

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey
    val id: String,
    val cashierId: String,
    val customerId: String? = null,
    val subtotal: Double,
    val discountAmount: Double = 0.0,
    val discountPercentage: Double = 0.0,
    val taxAmount: Double = 0.0,
    val totalAmount: Double,
    val notes: String? = null,
    val status: String, // PENDING, COMPLETED, VOIDED, REFUNDED
    val syncStatus: String = "PENDING", // PENDING, SYNCED, FAILED
    val createdAt: Date,
    val updatedAt: Date
)

@Entity(tableName = "order_items")
data class OrderItemEntity(
    @PrimaryKey
    val id: String,
    val orderId: String,
    val productId: String,
    val quantity: Int,
    val unitPrice: Double,
    val lineTotal: Double,
    val addOns: String? = null, // JSON serialized
    val createdAt: Date
)

@Entity(tableName = "payments")
data class PaymentEntity(
    @PrimaryKey
    val id: String,
    val orderId: String,
    val method: String, // CASH, GCASH, MAYA, CARD
    val amount: Double,
    val referenceNumber: String? = null,
    val status: String, // SUCCESS, PENDING, FAILED
    val createdAt: Date,
    val updatedAt: Date
)

@Entity(tableName = "customers")
data class CustomerEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val phoneNumber: String? = null,
    val email: String? = null,
    val loyaltyPoints: Int = 0,
    val totalSpent: Double = 0.0,
    val isActive: Boolean = true,
    val createdAt: Date,
    val updatedAt: Date
)

@Entity(tableName = "receipts")
data class ReceiptEntity(
    @PrimaryKey
    val id: String,
    val orderId: String,
    val receiptNumber: String,
    val content: String, // HTML or text content
    val printedAt: Date? = null,
    val createdAt: Date
)

@Entity(tableName = "sync_queue")
data class SyncQueueEntity(
    @PrimaryKey
    val id: String,
    val entityType: String, // ORDER, INVENTORY, etc
    val entityId: String,
    val action: String, // CREATE, UPDATE, DELETE
    val data: String, // JSON payload
    val status: String = "PENDING", // PENDING, SYNCED, FAILED
    val retryCount: Int = 0,
    val createdAt: Date,
    val updatedAt: Date
)

@Entity(tableName = "activity_logs")
data class ActivityLogEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val action: String,
    val entityType: String,
    val entityId: String,
    val details: String? = null,
    val createdAt: Date
)
