package com.demopos.common

class AppConstants {
    companion object {
        const val DATABASE_NAME = "demopos_database"
        const val DATABASE_VERSION = 1
        
        // Session constants
        const val SESSION_TIMEOUT_MINUTES = 30
        const val MAX_SESSION_RETRY = 3
        
        // UI constants
        const val ANIMATION_DURATION_MS = 300
        const val DEBOUNCE_DELAY_MS = 300L
        
        // Network
        const val API_TIMEOUT_SECONDS = 30L
        const val MAX_RETRIES = 3
        
        // Inventory
        const val LOW_STOCK_THRESHOLD = 10
        
        // Payment methods
        const val PAYMENT_CASH = "CASH"
        const val PAYMENT_GCASH = "GCASH"
        const val PAYMENT_MAYA = "MAYA"
        const val PAYMENT_CARD = "CARD"
        
        // Order status
        const val ORDER_STATUS_PENDING = "PENDING"
        const val ORDER_STATUS_COMPLETED = "COMPLETED"
        const val ORDER_STATUS_VOIDED = "VOIDED"
        const val ORDER_STATUS_REFUNDED = "REFUNDED"
        
        // User roles
        const val ROLE_ADMIN = "ADMIN"
        const val ROLE_CASHIER = "CASHIER"
        const val ROLE_MANAGER = "MANAGER"
    }
}
