package com.example.expensestracker.domain.usecase

import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.repository.ExpenseRepository
import com.example.expensestracker.domain.util.ExpenseOrder
import com.example.expensestracker.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetExpenses(
    private val repository: ExpenseRepository,
) {

    operator fun invoke(
        expenseOrder: ExpenseOrder = ExpenseOrder.Date(OrderType.Descending),
    ): Flow<List<Expense>> = repository.getExpenses().map { expenses ->
        when (expenseOrder.orderType) {
            is OrderType.Ascending -> when (expenseOrder) {
                is ExpenseOrder.Title -> expenses.sortedBy { it.title.lowercase() }
                is ExpenseOrder.Date -> expenses.sortedBy { it.timestamp }
                is ExpenseOrder.Category -> expenses.sortedBy { it.category.id }
                is ExpenseOrder.Price -> expenses.sortedBy { it.price.amount }
            }
            is OrderType.Descending -> when (expenseOrder) {
                is ExpenseOrder.Title -> expenses.sortedByDescending { it.title.lowercase() }
                is ExpenseOrder.Date -> expenses.sortedByDescending { it.timestamp }
                is ExpenseOrder.Category -> expenses.sortedByDescending { it.category.id }
                is ExpenseOrder.Price -> expenses.sortedByDescending { it.price.amount }
            }
        }
    }
}
