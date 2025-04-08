package com.example.expensestracker.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}