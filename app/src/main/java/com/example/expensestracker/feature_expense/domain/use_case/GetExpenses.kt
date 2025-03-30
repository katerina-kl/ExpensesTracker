package com.example.expensestracker.feature_expense.domain.use_case

import com.example.expensestracker.feature_expense.domain.model.Expense
import com.example.expensestracker.feature_expense.domain.repository.ExpenseRepository
import com.example.expensestracker.feature_expense.domain.util.ExpenseOrder
import com.example.expensestracker.feature_expense.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetExpenses(
    private val repository: ExpenseRepository
) {

    operator fun invoke(
        expenseOrder: ExpenseOrder = ExpenseOrder.Date(OrderType.Descending)
    ): Flow<List<Expense>> {
        return repository.getExpenses().map { expenses ->
            when(expenseOrder.orderType) {
                is OrderType.Ascending -> {
                    when(expenseOrder) {
                        is ExpenseOrder.Title -> expenses.sortedBy { it.title.lowercase() }
                        is ExpenseOrder.Date -> expenses.sortedBy { it.timestamp }
                        is ExpenseOrder.Color -> expenses.sortedBy { it.category.color }
                        is ExpenseOrder.Price -> expenses.sortedBy { it.price.amount }
                    }
                }
                is OrderType.Descending -> {
                    when(expenseOrder) {
                        is ExpenseOrder.Title -> expenses.sortedByDescending { it.title.lowercase() }
                        is ExpenseOrder.Date -> expenses.sortedByDescending { it.timestamp }
                        is ExpenseOrder.Color -> expenses.sortedByDescending { it.category.color }
                        is ExpenseOrder.Price -> expenses.sortedByDescending { it.price.amount }
                    }
                }
            }
        }
    }
}