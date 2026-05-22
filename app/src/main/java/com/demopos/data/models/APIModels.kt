package com.demopos.data.models

import java.util.Date

// Auth DTOs
data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: AuthTokenData? = null
)

data class AuthTokenData(
    val userId: String,
    val email: String,
    val name: String,
    val role: String,
    val token: String,
    val refreshToken: String?,
    val expiresAt: Long
)

// Product DTOs
data class ProductResponse(
    val id: String,
    val categoryId: String,
    val name: String,
    val description: String?,
    val price: Double,
    val cost: Double?,
    val barcode: String?,
    val imageUrl: String?,
    val isActive: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)

data class CategoryResponse(
    val id: String,
    val name: String,
    val description: String?,
    val imageUrl: String?,
    val displayOrder: Int,
    val isActive: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)

// Order DTOs
data class CreateOrderRequest(
    val customerId: String?,
    val items: List<OrderItemRequest>,
    val discountAmount: Double = 0.0,
    val taxAmount: Double = 0.0,
    val notes: String? = null,
    val payments: List<PaymentRequest>
)

data class OrderItemRequest(
    val productId: String,
    val quantity: Int,
    val unitPrice: Double,
    val addOns: List<String>? = null
)

data class PaymentRequest(
    val method: String,
    val amount: Double,
    val referenceNumber: String? = null
)

data class OrderResponse(
    val id: String,
    val cashierId: String,
    val customerId: String?,
    val subtotal: Double,
    val discountAmount: Double,
    val taxAmount: Double,
    val totalAmount: Double,
    val status: String,
    val createdAt: Long,
    val updatedAt: Long
)

// Sales Report DTOs
data class DailySalesResponse(
    val date: Long,
    val totalSales: Double,
    val orderCount: Int,
    val averageOrderValue: Double,
    val topProducts: List<TopProductData>
)

data class TopProductData(
    val productId: String,
    val productName: String,
    val quantity: Int,
    val totalSales: Double
)
