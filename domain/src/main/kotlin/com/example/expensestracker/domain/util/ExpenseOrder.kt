package com.example.expensestracker.domain.util

sealed class ExpenseOrder(val orderType: OrderType) {
    class Title(orderType: OrderType) : ExpenseOrder(orderType)
    class Date(orderType: OrderType) : ExpenseOrder(orderType)
    class Category(orderType: OrderType) : ExpenseOrder(orderType)
    class Price(orderType: OrderType) : ExpenseOrder(orderType)

    fun copy(orderType: OrderType): ExpenseOrder = when (this) {
        is Title -> Title(orderType)
        is Date -> Date(orderType)
        is Category -> Category(orderType)
        is Price -> Price(orderType)
    }
}
