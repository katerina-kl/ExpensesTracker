package com.example.expensestracker.feature_expense.presentation.expenses

import com.example.expensestracker.feature_expense.domain.model.Expense
import com.example.expensestracker.feature_expense.domain.util.ExpenseOrder
import com.example.expensestracker.feature_expense.domain.util.OrderType

data class ExpensesState(
    val expenses: List<Expense> = emptyList(),
    val expenseOrder: ExpenseOrder = ExpenseOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)