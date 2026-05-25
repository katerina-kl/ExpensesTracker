package com.example.expensestracker.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Expense(
    val id: Long = 0L,
    val title: String,
    val content: String,
    val timestamp: Long,
    val category: ExpenseCategory,
    val price: Price,
)
