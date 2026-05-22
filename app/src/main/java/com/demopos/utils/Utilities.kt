package com.demopos.utils

import java.util.Date
import java.util.UUID

object IdGenerator {
    fun generate(): String = UUID.randomUUID().toString()
}

object DateUtils {
    fun getCurrentDate(): Date = Date()
    
    fun formatDate(date: Date): String {
        return "${date.year}-${date.month}-${date.date}"
    }
}

object CurrencyUtils {
    fun formatCurrency(amount: Double, currencyCode: String = "PHP"): String {
        return "$currencyCode %.2f".format(amount)
    }
    
    fun parseCurrency(value: String): Double {
        return value.replace(Regex("[^\\d.]"), "").toDoubleOrNull() ?: 0.0
    }
}
