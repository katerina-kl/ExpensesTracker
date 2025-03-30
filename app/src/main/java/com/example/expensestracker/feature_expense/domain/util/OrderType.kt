package com.example.expensestracker.feature_expense.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}