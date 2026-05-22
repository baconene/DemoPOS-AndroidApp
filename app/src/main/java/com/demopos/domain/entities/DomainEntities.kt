package com.demopos.domain.entities

import java.util.Date

data class User(
    val id: String,
    val email: String,
    val name: String,
    val pin: String?,
    val role: String,
    val isActive: Boolean,
    val createdAt: Date,
    val updatedAt: Date
)

data class AuthSession(
    val id: String,
    val userId: String,
    val token: String,
    val refreshToken: String?,
    val expiresAt: Date,
    val createdAt: Date,
    val isActive: Boolean
)

data class Product(
    val id: String,
    val categoryId: String,
    val name: String,
    val description: String?,
    val price: Double,
    val cost: Double?,
    val barcode: String?,
    val imageUrl: String?,
    val isFavorite: Boolean,
    val isActive: Boolean,
    val createdAt: Date,
    val updatedAt: Date
)

data class Category(
    val id: String,
    val name: String,
    val description: String?,
    val imageUrl: String?,
    val displayOrder: Int,
    val isActive: Boolean,
    val createdAt: Date,
    val updatedAt: Date
)

data class Order(
    val id: String,
    val cashierId: String,
    val customerId: String?,
    val subtotal: Double,
    val discountAmount: Double,
    val discountPercentage: Double,
    val taxAmount: Double,
    val totalAmount: Double,
    val notes: String?,
    val status: String,
    val syncStatus: String,
    val createdAt: Date,
    val updatedAt: Date
)

data class Payment(
    val id: String,
    val orderId: String,
    val method: String,
    val amount: Double,
    val referenceNumber: String?,
    val status: String,
    val createdAt: Date,
    val updatedAt: Date
)

data class Customer(
    val id: String,
    val name: String,
    val phoneNumber: String?,
    val email: String?,
    val loyaltyPoints: Int,
    val totalSpent: Double,
    val isActive: Boolean,
    val createdAt: Date,
    val updatedAt: Date
)

data class Receipt(
    val id: String,
    val orderId: String,
    val receiptNumber: String,
    val content: String,
    val printedAt: Date?,
    val createdAt: Date
)
