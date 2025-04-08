package com.example.expensestracker.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExpenseEntity(
    val title: String,
    val content: String,
    val timestamp: Long,
    val categoryIcon: Int,
    val categoryName: String,
    val categoryColor: Int,
    val priceAmount: Double?,
    val priceCurrency: String,
    @PrimaryKey val id: Int? = null
)






